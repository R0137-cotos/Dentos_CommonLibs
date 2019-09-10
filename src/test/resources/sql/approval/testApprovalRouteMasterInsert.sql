insert into approval_route_master(id,version,approval_route_grp_id,approval_route_name,description,special_price_approval_flg,route_condition_formula,cond_determine_order)
values(1,0,1,'テスト1',null,1,'function () { return true; }',1)/
insert into approval_route_master(id,version,approval_route_grp_id,approval_route_name,description,special_price_approval_flg,route_condition_formula,cond_determine_order)
values(4,0,1,'テスト1_2','複数ルート1',1,'function () { return true; }',2)/
insert into approval_route_master(id,version,approval_route_grp_id,approval_route_name,description,special_price_approval_flg,route_condition_formula,cond_determine_order)
values(2,0,2,'テスト2',null,1,'function () { validate("解約日",test.coverPresentationDate); return test.coverPresentationDate == 4980; }',1)/
insert into approval_route_master(id,version,approval_route_grp_id,approval_route_name,description,special_price_approval_flg,route_condition_formula,cond_determine_order)
values(5,0,2,'テスト2_2','複数ルート2',1,'function () { return true; }',2)/
insert into approval_route_master(id,version,approval_route_grp_id,approval_route_name,description,special_price_approval_flg,route_condition_formula,cond_determine_order)
values(3,0,3,'テスト3',null,1,'function () { validate("テスト",test.name); return true; }',1)/
insert into approval_route_master(id,version,approval_route_grp_id,approval_route_name,description,special_price_approval_flg,route_condition_formula,cond_determine_order)
values(6,0,3,'テスト3_2','複数ルート3',1,'function () { return true; }',2)/