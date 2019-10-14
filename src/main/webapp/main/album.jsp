<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script>
    $(function () {
        $("#albumList").jqGrid({
            url:"${pageContext.request.contextPath}/album/queryByPage",
            datatype:"json",
            colNames:["ID","TITLE","COVER","SCORE","AUTHOR","BEAM","COUNT","PUBLISH_date","content"],
            colModel:[
                {
                    name:"id",align:"center"
                },
                {
                    name:"title",editable:true,align:"center"
                },
                {
                    name: "cover", editable: true,edittype:"file",align:"center",
                    formatter:function (cellvalue, options, rowObject) {
                        return "<img style='width:150px;height:50px' src='${pageContext.request.contextPath}/img/"+cellvalue+"'/>"}
                },
                {
                    name:"score",editable:true,align:"center"
                },
                {
                    name:"author",editable:true,align:"center"
                },
                {
                    name:"beam",editable:true,align:"center"
                },
                {
                    name:"count",align:"center",editable:true
                },
                {
                    name:"publishDate",align:"center"
                },
                {
                    name:"content",editable:true
                }
            ],
            styleUI:"Bootstrap",
            autowidth:true,
            height:"60%",
            pager:"#albumPager",
            page:1,
            rowNum:2,
            multiselect:true,
            rowList:[2,4,6],
            viewrecords:true,
            subGrid : true,
            align:"center",
            editurl: "${pageContext.request.contextPath}/album/edit",
            cellEdit: false,
            toolbar: [true, "top"],
            subGridRowExpanded:function (subgrid_id, albumId) {
                addSubGrid(subgrid_id,albumId);
            }
        }).jqGrid(
            "navGrid","#albumPager",
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
                    alert(banner)
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/album/upload",
                        fileElementId:"cover",
                        data:{banner:banner},
                        success:function (data) {
                            $("#albumList").trigger("reloadGrid");
                        }
                    });
                    return response
                }
            },
            {
                //在删除数据之前或者之后进行的额外的操作

            }
        )
    });



    function addSubGrid(subgrid_id,albumId) {
        var tableId=subgrid_id+"table";
        var divId=subgrid_id+"div";
        $("#"+subgrid_id).html(
            "<table id='"+tableId+"' ></table>"+"<div id='"+divId+"' ></div>"
        );
        $("#"+tableId).jqGrid({
            url:"${pageContext.request.contextPath}/chapter/queryByPage?albumId="+albumId,
            datatype:"json",
            colNames:["ID","FILEPATH","TITLE","SIZE","TIME","STATUS","CREATEDATE","OPTION"],
            colModel:[
                {
                    name:"id"
                },
                {
                    name: "filepath",align:"center",edittype:"file",editable: true,
                },
                {
                    name:"title",editable: true
                },
                {
                  name:"size"
                },
                {
                    name:"time"
                },
                {
                    name:"status",editable: true,align:"center",edittype:"select",
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
                {
                    name:"creatData",align:"center"
                },
                {
                    name:"",
                    formatter:function (cellValue, options, rowObject) {
                        return "<a href='${pageContext.request.contextPath}/music/"+rowObject.filepath+"'><span class='glyphicon glyphicon-play-circle'></span></a>"+"                       "+
                            "<a  href='${pageContext.request.contextPath}/chapter/download?filename="+rowObject.filepath+"'><span class='glyphicon glyphicon-download'></span></a>"
                    }
                }
            ],
            styleUI:"Bootstrap",
            autowidth:true,
            height:"60%",
            pager:"#"+divId,
            page:1,
            rowNum:2,
            multiselect:true,
            rowList:[2,4,6],
            viewrecords:true,
            align:"center",
            editurl: "${pageContext.request.contextPath}/chapter/edit?albumId="+albumId,
            cellEdit: false,
            toolbar: [true, "top"],
        }).jqGrid(
            "navGrid","#"+divId,
            {
                //在处理页面几个按钮的样式
                search:false
            },
            {
                //在编辑之前或者之后进行的额外的操作 beforeShowForm afterSubmit
                closeAfterEdit: true,
            },
            {
                //在添加数据之前或者之后进行的额外的操作
                closeAfterAdd:true,
                afterSubmit:function (response) {
                    var banner = response.responseText;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/chapter/upload",
                        fileElementId:"filepath",
                        data:{banner:banner},
                        success:function (data) {
                            $("#"+tableId).trigger("reloadGrid");
                            $("#albumList").trigger("reloadGrid");
                        }
                    });
                    return response
                }
            },
            {
                //在删除数据之前或者之后进行的额外的操作
                afterSubmit:function () {
                    $("#"+tableId).trigger("reloadGrid");
                    $("#albumList").trigger("reloadGrid");
                    return "adf";
                }

            }
        )
    }
</script>
<table id="albumList"></table>
<div id="albumPager"></div>