package com.blogaer.post.utils.helper;

import io.smallrye.mutiny.Uni;

public final class Helper {

    private Helper() {
    }

    public static Uni<Void> ackVoidAndLog(String message) {
        return Uni.createFrom().voidItem().invoke(() -> System.out.println(message));
    }

    public static <T> Uni<Void> ackVoidAndLog(Uni<T> upstreamUni) {
        return upstreamUni
                .invoke(item -> System.out.println(item))
                .replaceWithVoid()
                .onFailure()
                .invoke(failure -> System.err.println(failure.getMessage()));
    }
}
