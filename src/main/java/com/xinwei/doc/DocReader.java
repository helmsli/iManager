package com.xinwei.doc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

import com.xinwei.doc.vo.Contact;
import com.xinwei.doc.vo.CounterpartFunds;
import com.xinwei.doc.vo.Leader;
import com.xinwei.doc.vo.ProjectInfo;

public class DocReader {
	private Logger logger = Logger.getLogger(DocReader.class);

	public static void main(String[] args) {
		DocReader reader = new DocReader();
		String srcPath = "2017年福彩金项目申报书.doc";
		try {
			ProjectInfo readDoc = reader.readDoc(srcPath);

			System.out.println(readDoc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProjectInfo readDoc(String srcPath) {
		InputStream is;
		try {
			is = new FileInputStream(srcPath);
		} catch (FileNotFoundException e) {
			printError(e);
			return null;
		}
		return readDoc(is);
	}

	public ProjectInfo readDoc(InputStream is) {
		HWPFDocument doc = null;
		try {
			doc = new HWPFDocument(is);
			// 输出文本
			Range range = doc.getRange();

			// 读表格
			return readTable(range, doc);

		} catch (Exception e) {
			printError(e);
			return null;
		} finally {
			closeDoc(doc);
			closeStream(is);
		}
	}

	private void closeDoc(HWPFDocument doc) {
		try {
			if (doc != null) {
				doc.close();
			}
		} catch (Exception e) {
			printError(e);
		}
	}

	/**
	 * 关闭输入流
	 * 
	 * @param is
	 */
	private void closeStream(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读表格 每一个回车符代表一个段落，所以对于表格而言，每一个单元格至少包含一个段落，每行结束都是一个段落。
	 * 
	 * @param range
	 */
	private ProjectInfo readTable(Range range, HWPFDocument doc) {
		ProjectInfo projectInfo = new ProjectInfo();

		Table table = findTable(range, "项目名称", "申报单位", "申报单位类型", "开户银行", "银行账号");
		if (table != null) {
			readTableOrganization(doc, range, table, projectInfo);
		}

		table = findTable(range, "执行周期", "姓名", "职务", "办公电话", "电子邮箱");
		if (table != null) {
			readTableProject(doc, range, table, projectInfo);
		}

		table = findTable(range, "申报资金", "自有资金", "社会募集资金", "合计", "配套资金");
		if (table != null) {
			readTableFunds(doc, range, table, projectInfo);
		}

		return projectInfo;
	}

	private void readTableOrganization(HWPFDocument doc, Range range, Table table, ProjectInfo projectInfo) {
		int rowNum = table.numRows();
		for (int j = 0; j < rowNum; j++) {
			TableRow row = table.getRow(j);
			int cellNum = row.numCells();

			for (int k = 0; k < cellNum; k++) {
				try {
					TableCell cell = row.getCell(k);

					String cellText = cell.text().trim();

					if (cellText.equals("项目名称")) {
						k += 1;
						cellText = row.getCell(k).text();
						projectInfo.setName(cellText.trim());
					} else if (cellText.equals("申报单位")) {
						k += 1;
						cellText = row.getCell(k).text().trim();
						projectInfo.setDeclarationUnit(cellText.trim());
					} else if (cellText.equals("开户单位名称")) {
						k += 1;
						cellText = row.getCell(k).text();
						projectInfo.setOpeningUnit(cellText.trim());
					} else if (cellText.equals("开户银行")) {
						k += 1;
						cellText = row.getCell(k).text();
						projectInfo.setOpeningBank(cellText.trim());

					} else if (cellText.equals("银行账号")) {
						k += 1;
						cellText = row.getCell(k).text();
						projectInfo.setBankAccount(cellText.trim());

					} else if (cellText.contains("申报单位类型")) {
						k += 1;
						cell = row.getCell(k);
						Section s = cell.getSection(0);
						int numParagraphs = s.numParagraphs();
						for (int para = 0; para < numParagraphs; para++) {
							Paragraph paragraph = s.getParagraph(para);
							boolean bold = false;
							for (int z = 0; z < paragraph.numCharacterRuns(); z++) {
								if (projectInfo.getDeclarationUnitType() != null) {
									break;
								}
								CharacterRun run = paragraph.getCharacterRun(z);
								// 字符串文本
								String text = run.text().trim();
								bold = run.isBold() || run.isItalic();

								System.out.println(text + ":" + bold);
								if (bold && text.length() > 0) {
									if ("□民办非企业单位（社会服务机构）".contains(text)) {
										projectInfo.setDeclarationUnitType("民办非企业单位（社会服务机构）");

									} else if ("□社会团体".contains(text)) {
										projectInfo.setDeclarationUnitType("社会团体");

									} else if ("□基金会".contains(text)) {
										projectInfo.setDeclarationUnitType("基金会");
									}
								}
							}

						}
						if (projectInfo.getDeclarationUnitType() == null) {
							cellText = cell.text().trim();
							cellText = cellText.replaceAll("□民办非企业单位（社会服务机构）", "");
							cellText = cellText.replaceAll("□社会团体", "");
							cellText = cellText.replaceAll("□基金会", "");
							cellText = cellText.replaceAll("□", "");
							projectInfo.setDeclarationUnitType(cellText.trim());
						}

					}
				} catch (Exception e) {
					printError(e);
				}
			}
		}
	}

	String mailto = "HYPERLINK \"mailto:";

	private void readTableProject(HWPFDocument doc, Range range, Table table, ProjectInfo projectInfo) {
		int rowNum = table.numRows();
		for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {

			TableRow row = table.getRow(rowIndex);
			if (rowIndex == rowNum - 1) {
				projectInfo.setProjectDesc(row.getCell(0).text().trim());
			} else {
				int cellNum = row.numCells();
				for (int columnIndex = 0; columnIndex < cellNum; columnIndex++) {
					try {
						TableCell cell = row.getCell(columnIndex);
						String cellText = cell.text().trim();
						System.out.println(cellText);
						if (cellText.contains("申请金额")) {
							cellText = row.getCell(++columnIndex).text();
							projectInfo.setApplicationAmount(cellText.trim());

						} else if (cellText.contains("项目实施地域")) {
							cellText = row.getCell(++columnIndex).text();
							projectInfo.setProjectArea(cellText.trim());
						} else if (cellText.equals("执行周期")) {
							cellText = row.getCell(++columnIndex).text().trim();
							String[] split = cellText.split("至");
							String s = split[0];
							int year = s.indexOf("年");
							int month = s.indexOf("月");
							if (year == -1 || month == -1) {
								projectInfo.setStartDate(s);
							} else {
								projectInfo.setStartDate(
										s.substring(0, year).trim() + "-" + s.substring(year + 1, month).trim());
							}
							s = split[1];
							year = s.indexOf("年");
							month = s.indexOf("月");
							if (year == -1 || month == -1) {
								projectInfo.setEndDate(s);
							} else {
								projectInfo.setEndDate(
										s.substring(0, year).trim() + "-" + s.substring(year + 1, month).trim());
							}
						} else if (cellText.equals("项目负责人")) {
							String name = row.getCell(++columnIndex).text().trim();
							String job = row.getCell(++columnIndex).text().trim();
							String tel = row.getCell(++columnIndex).text().trim();
							String phone = row.getCell(++columnIndex).text().trim();
							
							cell = row.getCell(++columnIndex);
							String mail = cell.text().trim();
							if (mail.startsWith(mailto)) {
								// HYPERLINK "mailto:Test@163.com" Test@163.com
								mail = mail.substring(mailto.length());
								int indexOf = mail.indexOf("\"");
								mail = mail.substring(0, indexOf);
							}
							projectInfo.addContact(new Leader(name, job, tel, phone, mail));
						} else if (cellText.equals("项目联系人")) {
							String name = row.getCell(++columnIndex).text().trim();
							String job = row.getCell(++columnIndex).text().trim();
							String tel = row.getCell(++columnIndex).text().trim();
							String phone = row.getCell(++columnIndex).text().trim();

							cell = row.getCell(++columnIndex);
							String mail = cell.text().trim();
							if (mail.startsWith(mailto)) {
								// HYPERLINK "mailto:Test@163.com" Test@163.com
								mail = mail.substring(mailto.length());
								int indexOf = mail.indexOf("\"");
								mail = mail.substring(0, indexOf);
							}
							projectInfo.addContact(new Contact(name, job, tel, phone, mail));
						} else if (cellText.contains("项目简介")) {
							cellText = row.getCell(columnIndex).text();
							projectInfo.setProjectDesc(cellText.trim());
						} else if (cellText.contains("服务领域")) {
							cell = row.getCell(++columnIndex);
							Section s = cell.getSection(0);
							int numParagraphs = s.numParagraphs();
							for (int para = 0; para < numParagraphs; para++) {
								Paragraph paragraph = s.getParagraph(para);
								boolean bold = false;
								for (int z = 0; z < paragraph.numCharacterRuns(); z++) {
									if (projectInfo.getServiceField() != null) {
										break;
									}
									CharacterRun run = paragraph.getCharacterRun(z);
									// 字符串文本
									String text = run.text().trim();
									System.out.println(text + ":" + bold);
									bold = run.isBold() || run.isItalic();
									if (bold && text.length() > 0) {
										if ("□社会救助家庭服务".contains(text)) {
											projectInfo.setServiceField("社会救助家庭服务");
										} else if ("□困境老人服务".contains(text)) {
											projectInfo.setServiceField("困境老人服务");
										} else if ("□社会组织孵化培育体系建设".contains(text)) {
											projectInfo.setServiceField("社会组织孵化培育体系建设");
										}
									}

								}
							}
							if (projectInfo.getServiceField() == null) {
								cellText = cell.text().trim();
								cellText = cellText.replaceAll("□社会救助家庭服务", "");
								cellText = cellText.replaceAll("□困境老人服务", "");
								cellText = cellText.replaceAll("□社会组织孵化培育体系建设", "");
								cellText = cellText.replaceAll("□", "");
								projectInfo.setServiceField(cellText.trim());
							}
						}
					} catch (Exception e) {
						printError(e);
					}
				}
			}

		}
	}

	private void readTableFunds(HWPFDocument doc, Range range, Table table, ProjectInfo projectInfo) {
		CounterpartFunds counterpartFunds = new CounterpartFunds();

		int rowNum = table.numRows();
		for (int j = 0; j < rowNum; j++) {
			TableRow row = table.getRow(j);
			int cellNum = row.numCells();
			for (int k = 0; k < cellNum; k++) {
				try {
					TableCell cell = row.getCell(k);
					String cellText = cell.text().trim();

					if (cellText.equals("申报资金")) {
						k += 1;
						cellText = row.getCell(k).text();
						projectInfo.setDeclaredFunds(cellText.trim());
					}
					if (cellText.equals("自有资金")) {
						k += 1;
						cellText = row.getCell(k).text();
						counterpartFunds.setOwnFunds(cellText.trim());
					} else if (cellText.equals("社会募集资金")) {
						k += 1;
						cellText = row.getCell(k).text();
						counterpartFunds.setSocialFunds(cellText.trim());
					} else if (cellText.equals("合计")) {
						k += 1;
						cellText = row.getCell(k).text();
						counterpartFunds.setTotal(cellText.trim());
					}
				} catch (Exception e) {
					printError(e);
				}
			}
		}
		projectInfo.setCounterpartFunds(counterpartFunds);

	}

	private Table getTable(Range range, List<String> columnNameList) {
		// 遍历range范围内的table。
		TableIterator tableIter = new TableIterator(range);
		Table table;
		TableRow row;
		TableCell cell;

		while (tableIter.hasNext()) {
			table = tableIter.next();

			int rowNum = table.numRows();
			for (int j = 0; j < rowNum; j++) {
				row = table.getRow(j);
				int cellNum = row.numCells();

				for (int k = 0; k < cellNum; k++) {
					cell = row.getCell(k);
					String cellText = cell.text().trim();
					columnNameList.remove(cellText);
				}
			}
			if (columnNameList.isEmpty()) {
				return table;
			}
		}
		return null;
	}

	private Table findTable(Range range, String... columnNames) {
		// 遍历range范围内的table。
		TableIterator tableIter = new TableIterator(range);
		Table table;
		TableRow row;
		TableCell cell;
		List<String> columnNameList = new ArrayList(Arrays.asList(columnNames));
		while (tableIter.hasNext()) {
			table = tableIter.next();

			int rowNum = table.numRows();
			for (int j = 0; j < rowNum; j++) {
				row = table.getRow(j);
				int cellNum = row.numCells();

				for (int k = 0; k < cellNum; k++) {
					cell = row.getCell(k);
					String cellText = cell.text().trim();
					columnNameList.remove(cellText);
				}
			}
			if (columnNameList.isEmpty()) {
				return table;
			}
		}
		return null;
	}

	public void printError(Exception e) {
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
		}
		logger.error(sw);
	}
}