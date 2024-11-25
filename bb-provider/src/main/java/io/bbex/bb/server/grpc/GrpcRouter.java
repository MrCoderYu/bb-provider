package io.bbex.bb.server.grpc;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.bbex.base.account.NewOrderReply;
import io.bbex.base.account.NewOrderRequest;
import io.bbex.base.account.TestReply;
import io.bbex.base.account.TestRequest;
import io.bbex.base.protocol.exchange.TradeResponse;
import io.grpc.Context;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class GrpcRouter {

    @Resource
    Executor settleExecutor;

    public <T> void testAsyncCall(String functionName,
                                  ListenableFuture<TestReply> futureReply,
                                  StreamObserver<TestReply> responseObserver,
                                  TestRequest request) {
        Futures.addCallback(
                futureReply,
                new FutureCallback<TestReply>() {
                    @Override
                    public void onSuccess(@Nullable TestReply result) {
                        responseObserver.onNext(result);
                        responseObserver.onCompleted();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        responseObserver.onError(t);
                    }
                }
                , settleExecutor);
    }

}
