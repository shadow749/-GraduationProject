$(function () {
    initSelect()
})

function initSelect() {
$.ajax({
        url: '/pages/addselect.json',
        // url: 'http://localhost:8001/getSlaveNodeServices',
        type: 'get',
        dataType:"json",
        success: function (data) {
           if(data && data.rows){
               // var html="<option value=''>请选择源服务器</option>"
               var html="";
                for(var i = 0;i<data.rows.length;i++){
                    var temp = data.rows[i];
                    html+="<option value='"+temp.url+"'>"+temp.serviceName+"</option>"

                }
                $("#taskSel").html(html)
               taskSelChange();
           }
        }
    });
}
let gUrl=""
function taskSelChange() {
    gUrl = $("#taskSel").val();
    let setting = {
        check: {
            enable: true,
            chkStyle : "radio",
            autoCheckTrigger: false,
            radioType:"all",
            chkboxType: {"Y": "", "N": ""}
        },
        async: {
            enable: true,
            url:"/pages/folder.json",
            // url:"http://localhost:8001
            // /list",
            type:"get",
            autoParam:["fileUrl"],
            otherParam:{"sourceUrl":gUrl},
            dataFilter: ajaxDataFilter
        }
    };
    $.fn.zTree.init($("#treeDemo"), setting);
}
function ajaxDataFilter(treeId, parentNode, responseData) {
    if (responseData) {
        for(var i =0; i < responseData.length; i++) {
            if(!responseData[i].isParent){
                responseData[i].nocheck=true
            }
        }
    }
    return responseData;
};

function addTask() {
    let treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    let nodes = treeObj.getCheckedNodes();
    if(nodes && nodes.length>0){
        let fileUrl = nodes[0].fileUrl;
        console.log( fileUrl +"--"+ gUrl);
    }else{
        alert("请选择一个文件")
    }

}