package io.bbex.bb.server.grpc;

import com.toobit.tbsc.spring.boot.annotation.GrpcService;
import io.bbex.base.account.CancelSymbolOrdersReply;
import io.bbex.base.account.TestReply;
import io.bbex.base.account.TestServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
@Slf4j
public class TestGrpcImpl extends TestServiceGrpc.TestServiceImplBase {

    @Autowired
    private GrpcTestService grpcTestService;

    @Override
    public void test(io.bbex.base.account.TestRequest request,
                     io.grpc.stub.StreamObserver<io.bbex.base.account.TestReply> responseObserver) {
        log.info("test method,accountId:{}", request.getAccountId());
        // 同步调用下游
//        TestReply testReply = grpcTestService.test(request);

        // 直接返回
//        TestReply testReply = TestReply.newBuilder().build();

//        responseObserver.onNext(testReply);
//        responseObserver.onCompleted();
        // 异步调用下游
        grpcTestService.testAsync(request, responseObserver);
    }


}
