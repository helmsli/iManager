insert into tb_project_annex(annex_id,annex_name,original_filename,type_id,path,user_id,upload_time) values(1000,'monthlyReportTemplate.xls','月报表.xls',1,'monthlyReportTemplate.xls','7',NOW());

alter table tb_data_permission modify column ext_data2 varchar(228) CHARACTER SET utf8;
ALTER TABLE tb_data_permission  ADD INDEX tb_dsata_data_2 (ext_data2);
ALTER TABLE tb_data_permission  ADD INDEX tb_datap_all (category_id,data_type,permission_type,permission_id);

ALTER TABLE tb_data_permission DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


