package org.freedesktop.xjbgen.util;

import java.util.Collection;

/**
 * Describes a graph node-like type with the ability to retrieve the preceding nodes via the {@link #predecessors()}
 * function.
 *
 * @param <N> The type of the predecessors which, in most situations, will be the type implementing this interface.
 *
 * @see org.freedesktop.xjbgen.xml.XjbModule
 */
public interface PredecessorFunction<N extends PredecessorFunction<N>> {

    Collection<? extends N> predecessors();
}
