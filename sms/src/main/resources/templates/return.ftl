<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>付款成功</title>
    <link rel="stylesheet" href="css/success.css">
    <style>
        * {
            font-family: "微软雅黑";
        }
    </style>
</head>
<body ontouchstart="">
<div class="container js_container">
    <div class="page msg">
        <div class="bd">
            <div class="weui_msg" style="padding: 10px 0 0;">
                <div class="weui_icon_area"><i class="weui_icon_msg weui_icon_success_no_circle"></i></div>
                <div class="weui_text_area">
                    <h2 class="weui_msg_title">付款成功</h2>
                    <p class="weui_msg_desc">恭喜您，成功支付 ${money} 元</p>
                </div>
                <p class="weui_btn_area">
                    <a href="javascript:;" onclick="off();" class="weui_btn weui_btn_warn">关闭窗口</a>
                </p>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function off() {
        window.open("close.html", '_self');
        window.close();
    }
</script>
</body>
</html>