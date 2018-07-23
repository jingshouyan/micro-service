<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>安领支付</title>
    <link href="css/style.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/jquery/1.12.3/jquery.min.js"></script>
</head>
<body>
<div class="content">
    <form name="form1" action="https://api.92pay.cn/core/api/request/pay/" class="alipayform" method="post" hidden="true">
        <div class="element">
            <div class="etitle">
                商户编号(payId):
            </div>
            <div class="einput">
                <input type="text" name="payId" value="${payId}">
            </div>
        </div>
        <div class="element">
            <div class="etitle">
                支付通道(payChannel):
            </div>
            <div class="einput">
                <input type="text" name="payChannel" value="${payChannel}">
            </div>
        </div>
        <div class="element">
            <div class="etitle">
                订单标题(Subject):
            </div>
            <div class="einput">
                <input type="text" name="Subject" value="${Subject}">
            </div>
            <br>
        </div>
        <div class="element">
            <div class="etitle">
                金额(Money)
                <a style="color:#F00">
                    单位分
                </a>
                :
            </div>
            <div class="einput">
                <input type="text" name="Money" value="${Money}">
            </div>
            <br>
        </div>
        <div class="element">
            <div class="etitle">
                订单号(orderNumber):
            </div>
            <div class="einput">
                <input type="text" name="orderNumber" value="${orderNumber}">
            </div>
            <br>
        </div>
        <div class="element">
            <div class="etitle">
                附加数据(attachData):
            </div>
            <div class="einput">
                <input type="text" name="attachData" value="${attachData}">
            </div>
            <br>
        </div>
        <div class="element">
            <div class="etitle">
                异步通知(Notify_url):
            </div>
            <div class="einput">
                <input type="text" name="Notify_url" value="${Notify_url}">
            </div>
            <br>
        </div>
        <div class="element">
            <div class="etitle">
                同步通知(Return_url):
            </div>
            <div class="einput">
                <input type="text" name="Return_url" value="${Return_url}">
            </div>
            <br>
        </div>
        <div class="element">
            <div class="etitle">
                签名(Sign):
            </div>
            <div class="einput">
                <input type="text" name="Sign" value="${Sign}">
            </div>
            <br>
        </div>
        <div class="element">
            <input type="submit" class="alisubmit" value="确认支付" id="btn-pay">
        </div>
    </form>
</div>

<script>
    (function($){
        $("#btn-pay").click()
    })(jQuery);
</script>
</body>
</html>