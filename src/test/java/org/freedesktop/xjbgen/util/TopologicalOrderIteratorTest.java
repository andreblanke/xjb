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

    private static final class TestPredecessorFunction implements PredecessorFunction<TestPredecessorFunction> {

        private Collection<? extends TestPredecessorFunction> predecessors;

        private TestPredecessorFunction(@NotNull final TestPredecessorFunction... predecessors) {
            this.predecessors = Arrays.asList(predecessors);
        }

        @Override
        public Collection<? extends TestPredecessorFunction> predecessors() {
            return predecessors;
        }

        private void setPredecessors(final TestPredecessorFunction... predecessors) {
            this.predecessors = Arrays.asList(predecessors);
        }
    }

    @Test
    public void testHasNextFalseOnEmptyElementsIterator() {
        final var iterator = new TopologicalOrderIterator<TestPredecessorFunction>(List.of());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIteration() {
        final var element0 = new TestPredecessorFunction();
        final var element1 = new TestPredecessorFunction(element0);
        final var element2 = new TestPredecessorFunction(element0);
        final var element3 = new TestPredecessorFunction();
        final var element4 = new TestPredecessorFunction(element1, element2, element3);
        final var element5 = new TestPredecessorFunction(element4);

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
    public static Stream<Arguments> testThrowsIllegalArgumentExceptionWhenCycleDetected() {
        final Arguments sizeTwoCycleArguments;
        {
            final var element0 = new TestPredecessorFunction();
            final var element1 = new TestPredecessorFunction(element0);

            element0.setPredecessors(element1);

            sizeTwoCycleArguments = Arguments.of(List.of(element0, element1));
        }

        final Arguments selfCycleArguments;
        {
            final var element0 = new TestPredecessorFunction();
            final var element1 = new TestPredecessorFunction(element0);
            final var element2 = new TestPredecessorFunction();

            element2.setPredecessors(element0, element1, element2);

            selfCycleArguments = Arguments.of(List.of(element0, element1, element2));
        }
        return Stream.of(sizeTwoCycleArguments, selfCycleArguments);
    }

    @MethodSource
    @ParameterizedTest
    public <E extends PredecessorFunction<E>> void testThrowsIllegalArgumentExceptionWhenCycleDetected(
            @NotNull final Collection<E> elements) {
        assertThrows(IllegalArgumentException.class, () -> new TopologicalOrderIterator<>(elements));
    }
    // </editor-fold>
}
