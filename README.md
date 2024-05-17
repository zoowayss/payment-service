# 支付服务

是一个支持多种支付方式的支付服务模块，当前支持的支付方式有 **PayPal** 和 **Stripe**，可以根据自己的需求选择自己想要的功能

## Supports

- [✅] PayPal 一次性支付
- [✅] PayPal 订阅支付
- [✅] PayPal Webhook 回调
- [✅] Stripe 一次性支付
- [✅] Stripe 订阅支付
- [✅] Stripe Webhook 回调
- [✅] 订单查单定时任务

## How to use
在你的启动类上面加  @EnablePayment ，然后在 application.yml 中配置支付相关的信息即可拥有以上功能