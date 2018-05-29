insert into approval_route_master(id,approval_route_name,cond_determine_order,description,route_condition_formula,special_price_approval_flg,approval_route_grp_id)
values(1,'特価対象フラグon',1,null,'function () { return true; }',1,1);
insert into approval_route_master(id,approval_route_name,cond_determine_order,description,route_condition_formula,special_price_approval_flg,approval_route_grp_id)
values(2,'特価対象フラグon',1,null,'function () { return true; }',1,2);
insert into approval_route_master(id,approval_route_name,cond_determine_order,description,route_condition_formula,special_price_approval_flg,approval_route_grp_id)
values(3,'特価対象フラグoff',1,null,'function () { validate("解約日",estimation.coverPresentationDate); return estimation.coverPresentationDate == 4980; }',1,3);
insert into approval_route_master(id,approval_route_name,cond_determine_order,description,route_condition_formula,special_price_approval_flg,approval_route_grp_id)
values(4,'特価対象フラグoff',1,null,'function () { validate("掲示日",contract.cancelScheduledDate); return contract.cancelScheduledDate == 4980; }',1,4);