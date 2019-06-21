# This is yukikoma's HTTP server!


## リファレンス
https://docs.oracle.com/javase/jp/8/docs/api/allclasses-noframe.html

## socket

**socket**is one endpoint of a two-way communication link   
between two programs running on the network.

ソケットとは、TCP/IP通信を行うために使用されるライブラリ（API）であり、  
クライアントサイドでは、接続先のホストIP、ポート番号がセットされる必要がある。  
サーバー側は、どのポートでクライアントからのリクエストを待ち受けるのかを保持している必要がある。  

そのソケットを通じてクライアントと、サーバーは通信を行う。

### サーバー側の動き
* 初期化
* 待ち受けポートを指定
* 待ち受け開始
* 接続待ち
* （クライアントからのリクエストがあったら）
* 受信
* 送信
* 切断終了

### クライアント側のソケット
* 初期化
* 接続
* 送信
* （サーバーからのレスポンスを待って）
* 受信
* 切断
* 終了

### サーバーステイタス
サーバーさん「このポートで待ってますよー」  
→Listen （ソケット作成）  

他にも  
- TIME_WAIT
- CLOSE_WAIT
- ESTABLISH  
がある。

#### おまけ
[![Image from Gyazo](https://i.gyazo.com/070ef7a8d9f8c9d93809fa2de1d3ef43.png)](https://gyazo.com/070ef7a8d9f8c9d93809fa2de1d3ef43)


### 参考文献
[<sys/socket.h>](https://pubs.opengroup.org/onlinepubs/009695399/basedefs/sys/socket.h.html)

[socket(2) - Linux manual page](http://man7.org/linux/man-pages/man2/socket.2.html)

All About Sockets
https://docs.oracle.com/javase/tutorial/networking/sockets/index.html

[ソケットの基本的な使用 (ネットワークインタフェース)](https://docs.oracle.com/cd/E19455-01/806-2730/6jbu725kj/index.html)

[【図解】初心者にも分かる TCP/UDP 〜違いや共通点,使い分け,ポート番号,具体例について〜  |  SEの道標](https://milestone-of-se.nesuke.com/nw-basic/tcp-udp/tcp-udp-summary/)



# References

## HTTP-related specification

- [RFC7230 Message Syntax and Routing](https://httpwg.org/specs/rfc7230.html)
- [RFC7231 Semantics and Content](https://httpwg.org/specs/rfc7231.html)
- [RFC7232 Conditional Requests](https://httpwg.org/specs/rfc7232.html)
- [RFC7234 Caching](https://httpwg.org/specs/rfc7234.html) 
