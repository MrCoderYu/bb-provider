package io.bbex.bb.server.grpc;

import com.google.common.util.concurrent.ListenableFuture;
import io.bbex.base.account.TestReply;
import io.bbex.base.account.TestRequest;
import io.bbex.base.account.TestServiceGrpc;
import io.bbex.bb.server.config.GrpcStubs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class GrpcTestService {

    @Resource
    GrpcStubs grpcStubs;

    @Resource
    GrpcRouter grpcRouter;

    public TestReply test(TestRequest request) {
        TestServiceGrpc.TestServiceBlockingStub stub = grpcStubs.testServiceBlockingStub();
        return stub.test(request);
    }

    public void testAsync(TestRequest request, io.grpc.stub.StreamObserver<io.bbex.base.account.TestReply> responseObserver) {
        ListenableFuture<TestReply> listenableFuture = grpcStubs.getTestFutureStub().test(request);
        grpcRouter.testAsyncCall("test",
                listenableFuture,
                responseObserver, request);
    }
}
