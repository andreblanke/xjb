package org.freedesktop.xjbgen.util;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

public final class TopologicalOrderIterator<E extends PredecessorFunction<E>> implements Iterator<E> {

    private int remainingElements;

    private E current;

    private final Collection<E> elements;

    private final Queue<E> queue = new ArrayDeque<>();

    private final Map<E, Integer> predecessorCount;

    public TopologicalOrderIterator(@NotNull final Collection<E> elements) {
        this.elements = elements;

        predecessorCount = new IdentityHashMap<>(elements.size());

        for (E element : elements) {
            final Collection<? extends E> predecessors = element.predecessors();

            predecessorCount.put(element, predecessors.size());

            if (predecessors.size() == 0) {
                queue.add(element);
            } else {
                final boolean hasCircle =
                    predecessors
                        .stream()
                        .flatMap(predecessor -> predecessor.predecessors().stream())
                        .anyMatch(predecessorsPredecessor -> Objects.equals(predecessorsPredecessor, element));

                if (hasCircle)
                    throw new IllegalArgumentException();
            }
        }
        remainingElements = elements.size();
    }

    @Override
    public boolean hasNext() {
        return (current != null) || ((current = advance()) != null);
    }

    @Override
    public E next() {
        if (!hasNext())
            throw new NoSuchElementException();
        final E result = current;

        current = null;

        return result;
    }

    private E advance() {
        final E result = queue.poll();

        if ((result == null) && (remainingElements > 0))
            throw new IllegalStateException();

        if (result != null) {
            for (E successor : successors(result)) {
                int count = predecessorCount.get(successor);

                if (count > 0) {
                    predecessorCount.put(successor, --count);

                    if (count == 0)
                        queue.add(successor);
                }
            }
            --remainingElements;
        }
        return result;
    }

    private Iterable<? extends E> successors(final E element) {
        return elements
            .stream()
            .filter(other -> other.predecessors().contains(element))
            .collect(Collectors.toList());
    }
}
