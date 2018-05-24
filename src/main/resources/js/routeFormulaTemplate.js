// 結果取得用クラスをインポート
load("nashorn:mozilla_compat.js");
importClass(Packages.jp.co.ricoh.cotos.commonlib.common.result.RouteFormulaResult);

var ArrayList = Java.type('java.util.ArrayList');
var resultEnum = Java.type("jp.co.ricoh.cotos.commonlib.common.result.RouteFormulaResult.RouteFormulaStatus");


// インポートしたクラスを初期化
result = new RouteFormulaResult();
result.status = resultEnum.正常;
result.errorPropertyList = new ArrayList();

// List内から対象オブジェクトを特定
function getObjectInList(list, filterTarget, filterValue) {
    return list.stream().filter(function(e) eval('e.' + filterTarget) == filterValue).findFirst().get();
}

// Javascriptの配列から対象オブジェクトを特定
function getObjectInJsArray(array, filterTarget, filterValue) {
    return array.filter(function(e) eval('e.'+filterTarget) == filterValue)[0];
}

// エラー処理
function validate(name,value) {

    // 項目が存在しているかの確認
    if (value === undefined) {
    
        // 警告エラー発生後に異常エラーが発生した場合は、項目名を上書き
        if (result.status == resultEnum.警告) {
            result.errorPropertyList = new ArrayList();
        }
    
        result.status = resultEnum.異常;
        result.errorPropertyList.add(name);
    }
    
    // 項目に値が存在しているかの確認（すでに異常エラーが発生している場合は無視）
    if (result.status != resultEnum.異常 && (value === null || value === "")) {
        result.status = resultEnum.警告;
        result.errorPropertyList.add(name);
    }
}

// ルート条件式を実行
result.applyRoute = {{{ROUTE_FORMULA}}}.call();
