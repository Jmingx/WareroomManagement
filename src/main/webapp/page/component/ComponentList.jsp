<%--
  Created by IntelliJ IDEA.
  User: 78289
  Date: 2020-05-24
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>零件信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/static/css/oksub.css">
    <script type="text/javascript" src="/static/lib/loading/okLoading.js"></script>
</head>
<body>
<div class="ok-body">

    <div class="layui-row">
        <form class="layui-form layui-col-md12 ok-search">

            <input id="componentName" class="layui-input" placeholder="零件名称" autocomplete="off" name="name">
            <button class="layui-btn" lay-submit lay-filter="search">
                <i class="layui-icon">&#xe615;</i>
            </button>
        </form>
    </div>

    <table id="applyTable" lay-filter="applyTable"></table>
</div>
<script src="/static/lib/layui/layui.js"></script>
<script type="text/javascript">
    layui.use(["element", "jquery", "okLayer", "form", "util", "okUtils", "table"], function () {
        let okLayer = layui.okLayer;
        let okUtils = layui.okUtils;
        let $ = layui.jquery;
        let table = layui.table;
        let form = layui.form;
        let util = layui.util;

        okLoading.close($);

        let applyTable = table.render({
            elem: '#applyTable',
            url: '/component?type=show',
            limit: 20,
            page: true,
            toolbar: true,
            toolbar: '#toolbarApply',
            size: "sm",
            response: {
                statusName: 'code',
                statusCode: 200
            },
            cols: [[
                {type: "checkbox", fixed: "left"},
                {field: "id", title: "ID", width: 160, sort: true},
                {field: "supplierId", title: "供应商编号", width: 160, sort: true},
                {field: "price", title: "目前价格", width: 160, sort: true},
                {field: "name", title: "零件名称", width: 160, sort: true},
                {title: "操作", width: 150, align: "center", fixed: "right", templet: "#operationTpl"}
            ]]
        });

        table.on("toolbar(applyTable)", function (obj) {
            switch (obj.event) {
                case "batchDel":
                    batchDel();
                    break;
                case "applyDevice":
                    buyDevice();
                    break;
            }
        });
        table.on('tool(applyTable)', function (obj) {
            let data = obj.data;
            switch (obj.event) {
                case 'delete':
                    deleteApply(data);
                    break;
                case 'edit':
                    editApply(data);
                    break;
                case 'buy':
                    buyDevice(data);
                    break;
                case 'open':
                    openApply(data);
            }
        });

        // form.on('select(state)', function (data) {
        //     applyTable.reload({
        //         where: {
        //             state: data.value
        //         },
        //         page: {curr: 1}
        //     });
        //     return false;
        // });

        form.on('submit(search)', function (data) {
            applyTable = table.render({
                elem: '#applyTable',
                url: '/component?type=search&componentName='+$("#componentName").val(),
                limit: 20,
                page: true,
                toolbar: true,
                toolbar: '#toolbarApply',
                size: "sm",
                response: {
                    statusName: 'code',
                    statusCode: 200
                },
                cols: [[
                    {type: "checkbox", fixed: "left"},
                    {field: "id", title: "ID", width: 160, sort: true},
                    {field: "supplierId", title: "供应商编号", width: 160, sort: true},
                    {field: "price", title: "目前价格", width: 160, sort: true},
                    {field: "name", title: "零件名称", width: 160, sort: true},
                    {title: "操作", width: 150, align: "center", fixed: "right", templet: "#operationTpl"}
                ]]
            });
            return false;
        });

        function batchDel() {
            okLayer.confirm("确定要批量删除吗？", function (index) {
                layer.close(index);
                let ids = tableBatchCheck();
                if (ids) {
                    //注意get和post
                    okUtils.ajax("/component?type=batchDelete", "get", {"ids": ids}, true).done(function (rep) {
                        okUtils.tableSuccessMsg(rep.message);
                    }).fail(function (rep) {
                        console.log(rep);
                    })

                }
            });
        }

        // function applyDevice() {
        //     okLayer.open("申购设备", "/system/addApply", "90%", "90%", null, function () {
        //         $(".layui-laypage-btn")[0].click();
        //     });
        // }

        function deleteApply(data) {
            okLayer.confirm("确定要删除吗？", function () {
                okUtils.ajax("/component?type=delete", "get", {id: data.id}, true).done(function (rep) {
                    console.log(rep);
                    okUtils.tableSuccessMsg(rep.message);
                }).fail(function (rep) {
                    console.log(rep);
                })

            });
        }

        function editApply(data) {
            okLayer.open("更新零件信息", "/page/component/editComponent.jsp", "90%", "90%", function (layero) {
                let iframeWin = window[layero.find("iframe")[0]["name"]];
                iframeWin.initForm(data);
            }, function () {
                applyTable.reload();
            })
        }

        function buyDevice(data) {
            okLayer.open("增加零件", "/page/component/addComponent.jsp", "90%", "90%", function (layero) {
                let iframeWin = window[layero.find("iframe")[0]["name"]];
                iframeWin.initForm(data);
            }, function () {
                $(".layui-laypage-btn")[0].click();
            })

        }

        function openApply(data) {
            okLayer.open("零件详情", "/page/component/openComponent.jsp", "90%", "90%", function (layero) {
                let iframeWin = window[layero.find("iframe")[0]["name"]];
                // data.applyDate = util.toDateString(data.applyDate, "yyyy-MM-dd");
                iframeWin.initForm(data);
            }, null)
        }


        function tableBatchCheck() {
            let checkStatus = table.checkStatus("applyTable");
            let rows = checkStatus.data.length;
            if (rows > 0) {
                let idsStr = "";
                for (let i = 0; i < checkStatus.data.length; i++) {
                    idsStr += checkStatus.data[i].id + ",";
                }
                return idsStr;
            } else {
                layer.msg("未选择有效数据", {offset: "t", anim: 6});
            }
        }

    });

</script>

<!-- 头工具栏模板 -->
<script type="text/html" id="toolbarApply">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="batchDel">批量删除</button>
        <button class="layui-btn layui-btn-sm" lay-event="applyDevice">增加零件</button>
    </div>
</script>
<script type="text/html" id="operationTpl">
    <a href="javascript:" title="详情" lay-event="open"><i class="layui-icon">&#xe705;</i></a>
    <a href="javascript:" title="编辑" lay-event="edit"><i class="layui-icon">&#xe642;</i></a>
    <a href="javascript:" title="删除" lay-event="delete"><i class="layui-icon">&#xe640;</i></a>
    <%--    <a href="javascript:" title="增加" lay-event="buy"><i class="layui-icon">&#xe61f;</i></a>--%>
</script>
</body>
</html>

