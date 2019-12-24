package org.freedesktop.xjbgen.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class TopologicalOrderIteratorTest {

    private static final class TestPredecessorFunction implements PredecessorFunction<TestPredecessorFunction> {

        private Collection<? extends TestPredecessorFunction> predecessors;

        private TestPredecessorFunction() {
            this(Collections.emptyList());
        }

        private TestPredecessorFunction(@NotNull final Collection<? extends TestPredecessorFunction> predecessors) {
            this.predecessors = predecessors;
        }

        @Override
        public Collection<? extends TestPredecessorFunction> predecessors() {
            return predecessors;
        }

        private void setPredecessors(final Collection<? extends TestPredecessorFunction> predecessors) {
            this.predecessors = predecessors;
        }
    }

    @Test
    void testThrowsIllegalArgumentExceptionWhenCycleDetected() {
        final var element0 = new TestPredecessorFunction();
        final var element1 = new TestPredecessorFunction(List.of(element0));

        element0.setPredecessors(List.of(element1));

        assertThrows(IllegalArgumentException.class, () -> new TopologicalOrderIterator<>(List.of(element0, element1)));
    }
}
