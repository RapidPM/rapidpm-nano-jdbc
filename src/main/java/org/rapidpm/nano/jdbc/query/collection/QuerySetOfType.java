package org.rapidpm.nano.jdbc.query.collection;

import java.util.Collection;
import java.util.HashSet;

public interface QuerySetOfType<T> extends QueryCollectionOfType<T> {
  @Override
  default Collection<T> createCollection() {
    return new HashSet<>();
  }
}
