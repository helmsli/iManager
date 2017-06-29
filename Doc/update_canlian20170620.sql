delete from tb_project_annex where annex_id=1002;
delete from tb_project_annex where annex_id=1003;
insert into tb_project_annex(annex_id,annex_name,original_filename,type_id,path,user_id,upload_time) values(1001,'canlian_application.docx','申请购买助残服务项目申报书2018.docx',1,'canlian_application.docx','7',NOW());
insert into tb_project_annex(annex_id,annex_name,original_filename,type_id,path,user_id,upload_time) values(1002,'canlian_application_ding.docx','定向购买助残服务项目申报书2018.docx',1,'canlian_application_ding.docx','7',NOW());
insert into tb_project_annex(annex_id,annex_name,original_filename,type_id,path,user_id,upload_time) values(1003,'canlian_application_wei.docx','社区助残公益微创投项目申报书2018.docx',1,'canlian_application_wei.docx','7',NOW());


