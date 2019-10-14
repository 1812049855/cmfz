<%@page isELIgnored="false" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="cc" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>
<script>
    $(function () {
        $("#users").jqGrid({
            styleUI: "Bootstrap",
            url: "${app}/banner/queryByPage",/*分页查询的路径*/
            datatype: "json",
            colNames:["ID","TITLE","STATUS","DESC","CREATEDATE","PATH"],   //表格标题
            autowidth: true,
            pager: "#page",
            rowNum: 2,
            viewrecords: true,
            align:"center",
            rowList: [2, 4, 6],/*下拉框展示的条数*/
            editurl: "${app}/banner/edit",
            cellEdit: false,
            multiselect:true,
            height:"69%",
            colModel: [
                {name: "id",align:"center"},
                {name: "title", editable: true,align:"center"},
                {name:"status",editable: true,align:"center",edittype:"select"	,
                    editoptions:
                        {
                            value:"1:正在展示;2:不在展示"
                    },
                    formatter:function(cellvalue, options, rowObject){
                        if(rowObject.status==1){
                            return "正在展示"
                        }
                        if(rowObject.status==2){
                            return "不在展示"
                        }
                    }
                },
                {name:"desc", editable: true,align:"center"},
                {name:"createDate",align:"center"},
                {name: "path", editable: true,edittype:"file",
                    formatter:function (cellvalue, options, rowObject) {
                       return "<img style='width:150px;height:50px' src='${pageContext.request.contextPath}/img/"+cellvalue+"'/>"}
                }
        ],
    }).jqGrid(
        "navGrid","#page",
            {
                //在处理页面几个按钮的样式
                search:false
            },
            {
                //在编辑之前或者之后进行的额外的操作 beforeShowForm afterSubmit
                closeAfterEdit:true,
            },
            {
                //在添加数据之前或者之后进行的额外的操作
                closeAfterAdd:true,
                afterSubmit:function (response) {
                    var banner = response.responseText;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/upload",
                        fileElementId:"path",
                        data:{banner:banner},
                        success:function (data) {
                            $("#users").trigger("reloadGrid");
                        }
                    });
                    return response
                }
            },
            {
                //在删除数据之前或者之后进行的额外的操作

            }
        )
    })
    function  showModal() {
            location.href="${pageContext.request.contextPath}/easypoi/download"
    }
</script>

<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" role="tab" data-toggle="tab">轮播图列表</a></li>
        <li ><a href="#" onclick="showModal()">导出信息</a></li>
    </ul>
</div>
<table id="users"></table>
<div id="page"></div>