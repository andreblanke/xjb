package org.freedesktop.xjbgen.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public final class TopologicalOrderIteratorTest {

    private static final class MockPredecessorFunction implements PredecessorFunction<MockPredecessorFunction> {

        private Collection<? extends MockPredecessorFunction> predecessors;

        private MockPredecessorFunction(@NotNull final MockPredecessorFunction... predecessors) {
            this.predecessors = Arrays.asList(predecessors);
        }

        @Override
        public Collection<? extends MockPredecessorFunction> predecessors() {
            return predecessors;
        }

        private void setPredecessors(final MockPredecessorFunction... predecessors) {
            this.predecessors = Arrays.asList(predecessors);
        }
    }

    @Test
    public void testHasNextFalseOnEmptyElementsIterator() {
        final var iterator = new TopologicalOrderIterator<MockPredecessorFunction>(List.of());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIteration() {
        final var element0 = new MockPredecessorFunction();
        final var element1 = new MockPredecessorFunction(element0);
        final var element2 = new MockPredecessorFunction(element0);
        final var element3 = new MockPredecessorFunction();
        final var element4 = new MockPredecessorFunction(element1, element2, element3);
        final var element5 = new MockPredecessorFunction(element4);

        element2.setPredecessors(element3);

        final var iterator = new TopologicalOrderIterator<>(
            List.of(element0, element1, element2, element3, element4, element5));

        assertTrue(iterator.hasNext());
        assertEquals(element0, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(element3, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(element1, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(element2, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(element4, iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(element5, iterator.next());
    }

    // <editor-fold desc="testThrowsIllegalArgumentExceptionWhenCycleDetected">
    @NotNull
    @Contract(pure = true)
    @SuppressWarnings("unused")
    public static Stream<Arguments> testThrowsIllegalArgumentExceptionWhenSimpleCycleDetected() {
        final Arguments size2CycleArguments;
        {
            final var element0 = new MockPredecessorFunction();
            final var element1 = new MockPredecessorFunction(element0);

            element0.setPredecessors(element1);

            size2CycleArguments = Arguments.of(List.of(element0, element1));
        }

        final Arguments selfCycleArguments;
        {
            final var element0 = new MockPredecessorFunction();
            final var element1 = new MockPredecessorFunction(element0);
            final var element2 = new MockPredecessorFunction();

            element2.setPredecessors(element0, element1, element2);

            selfCycleArguments = Arguments.of(List.of(element0, element1, element2));
        }
        return Stream.of(size2CycleArguments, selfCycleArguments);
    }

    @MethodSource
    @ParameterizedTest
    public <E extends PredecessorFunction<E>> void testThrowsIllegalArgumentExceptionWhenSimpleCycleDetected(
            @NotNull final Collection<E> elements) {
        assertThrows(IllegalArgumentException.class, () -> new TopologicalOrderIterator<>(elements));
    }
    // </editor-fold>
}
