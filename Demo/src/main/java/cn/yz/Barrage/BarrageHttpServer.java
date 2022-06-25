package cn.yz.Barrage;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName BarrageHttpServer
 * @date 2022/6/25 20:09
 */
public class BarrageHttpServer {

    //开放端口
    public void openServer(int port){
        ServerBootstrap bootstrap = new ServerBootstrap();
        //指定管道
        bootstrap.channel(NioServerSocketChannel.class);
        //主线程(1个)和work线程(8个)
        EventLoopGroup main = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup(8);
        //工作线程，第一个为主线程，第二个为work线程
        bootstrap.group(main,work);

        //在管道内部添加处理器，以便于实现解码和编码，netty的内部由pipeline实现
        bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                //添加管道的编码器和解码器
                nioSocketChannel.pipeline().addLast("http-decode",new HttpRequestDecoder());
                nioSocketChannel.pipeline().addLast("http-encode",new HttpRequestEncoder());
                //servlet 处理业务请求

            }
        });
    }



}
