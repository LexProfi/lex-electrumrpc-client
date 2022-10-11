# lex-electrumrpc-client

A lightweight client for connecting via RPC to an electrum bitcoin wallet.

Attention! Use this library only on a single local machine with an Electrum wallet! For security reasons, you should exclude the possibility of accessing the RPC interface of the Electrum wallet from the network.

Maven
=====
The package is not published to the maven repository, and you need to install it to your local repositories before using it:
```mvn clean install```

And add it to you pom.xml adding a section like this:

```
<dependency>
    <groupId>lex.electrumrpc</groupId>
	<artifactId>rpc-client</artifactId>
	<version>1.0.0</version>
</dependency>
```

And just create a `ElectrumJSONRPCClient` object passing the appropriate initialization parameters to it:

```
ElectrumJSONRPCClient electrumRpcClient = new ElectrumJSONRPCClient("http://user:password@server:port/");
```

Contacts
===
For faster communication with me, use the telegram messenger https://t.me/LexProfi