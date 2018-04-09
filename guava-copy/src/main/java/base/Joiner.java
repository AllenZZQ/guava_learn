package base;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import static base.Preconditions.checkNotNull;

public class Joiner {
    public static Joiner on(String separator) {
        return new Joiner(separator);
    }

    public static Joiner on(char separator) {
        return on(String.valueOf(separator));
    }

    private final String separator;

    private Joiner(String separator) {
        this.separator = checkNotNull(separator);
    }

    private Joiner(Joiner prototype) {
        this.separator = prototype.separator;
    }

    public <A extends Appendable> A appendTo(A appendable, Iterator<?> parts) throws IOException {
        checkNotNull(appendable);
        if (parts.hasNext()) {
            appendable.append(toString(parts.next()));
            while (parts.hasNext()) {
                appendable.append(separator);
                appendable.append(toString(parts.next()));
            }
        }
        return appendable;
    }

    public final <A extends Appendable> A appendTo(A appendable, Iterable<?> parts) throws IOException {
        return appendTo(appendable, parts.iterator());
    }

    public final <A extends Appendable> A appendTo(A appendable, Object[] parts) throws IOException {
        return appendTo(appendable, Arrays.asList(parts));
    }

    public final <A extends Appendable> A appendTo(A appendable, Object first, Object second, Object... rest) throws IOException {
        return appendTo(appendable, iterable(first, second, rest));
    }

    public final StringBuilder appendTo(StringBuilder builder, Object first, Object second, Object... rest) {
        return appendTo(builder, iterable(first, second, rest));
    }

    public final StringBuilder appendTo(StringBuilder builder, Iterable<?> parts) {
        return appendTo(builder, parts.iterator());
    }

    public final StringBuilder appendTo(StringBuilder builder, Iterator<?> parts) {
        try {
            appendTo((Appendable) builder, parts);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return builder;
    }

    public final StringBuilder appendTo(StringBuilder builder, Object[] parts) {
        return appendTo(builder, Arrays.asList(parts));
    }

    public final String join(Iterable<?> parts) {
        return join(parts.iterator());
    }

    public final String join(Iterator<?> parts) {
        return appendTo(new StringBuilder(), parts).toString();
    }

    public final String join(Object[] parts) {
        return join(Arrays.asList(parts));
    }

    public final String join(Object first, Object second, Object... rest) {
        return join(iterable(first, second, rest));
    }

    public Joiner skipNulls() {
        return new Joiner(this) {
            @Override
            public <A extends Appendable> A appendTo(A appendable, Iterator<?> parts) throws IOException {
                checkNotNull(appendable);
                checkNotNull(parts);
                while (parts.hasNext()) {
                    Object obj = parts.next();
                    if (obj != null) {
                        appendable.append(Joiner.this.toString(obj));
                    }
                }
                while (parts.hasNext()) {
                    Object obj = parts.next();
                    if (obj != null) {
                        appendable.append(separator);
                        appendable.append(Joiner.this.toString(obj));
                    }
                }
                return appendable;
            }

            @Override
            public Joiner useForNull(String nullText) {
                throw new UnsupportedOperationException();
            }
        };
    }

    public Joiner useForNull(final String nullText) {
        checkNotNull(nullText);
        return new Joiner(this) {
            @Override
            CharSequence toString(Object obj) {
                return (obj == null) ? nullText : Joiner.this.toString(obj);
            }

            @Override
            public Joiner skipNulls() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public MapJoiner withKeyValueSeparator(String keyValueSeparator) {
        return new MapJoiner(this, keyValueSeparator);
    }

    public MapJoiner withKeyValueSeparator(char keyValueSeparator) {
        return withKeyValueSeparator(String.valueOf(keyValueSeparator));
    }

    public static class MapJoiner {
        private final Joiner joiner;
        private final String keyValueSeparator;

        public MapJoiner(Joiner joiner, String keyValueSeparator) {
            this.joiner = joiner;
            this.keyValueSeparator = checkNotNull(keyValueSeparator);
        }

        public <A extends Appendable> A appendTo(A appendable, Map<?, ?> map) throws IOException {
            return appendTo(appendable, map.entrySet());
        }

        private <A extends Appendable> A appendTo(A appendable, Iterable<? extends Entry<?, ?>> entries) throws IOException {
            return appendTo(appendable, entries.iterator());
        }

        public <A extends Appendable> A appendTo(A appendable, Iterator<? extends Entry<?, ?>> entries) throws IOException {
            checkNotNull(appendable);
            if (entries.hasNext()) {
                Entry<?, ?> entry = entries.next();
                appendable.append(joiner.toString(entry.getKey()));
                appendable.append(keyValueSeparator);
                appendable.append(joiner.toString(entry.getValue()));
                while (entries.hasNext()) {
                    appendable.append(joiner.separator);
                    Entry<?, ?> e = entries.next();
                    appendable.append(joiner.toString(e.getKey()));
                    appendable.append(keyValueSeparator);
                    appendable.append(joiner.toString(e.getValue()));
                }
            }
            return appendable;
        }

        public StringBuilder appendTo(StringBuilder builder, Map<?, ?> map) {
            return appendTo(builder, map.entrySet());
        }

        public StringBuilder appendTo(StringBuilder builder, Iterable<? extends Entry<?, ?>> entries) {
            return appendTo(builder, entries.iterator());
        }

        public StringBuilder appendTo(StringBuilder builder, Iterator<? extends Entry<?, ?>> entries) {
            checkNotNull(builder);
            try {
                appendTo((Appendable) builder, entries);
            } catch (IOException e) {
                throw new AssertionError();
            }
            return builder;
        }

        public String join(Iterator<? extends Entry<?, ?>> entries) {
            return appendTo(new StringBuilder(), entries).toString();
        }

        public String join(Iterable<? extends Entry<?, ?>> entries) {
            return join(entries.iterator());
        }

        public String join(Map<?, ?> map) {
            return join(map.entrySet());
        }

        public MapJoiner useForNull(String nullText) {
            return new MapJoiner(joiner.useForNull(nullText), keyValueSeparator);
        }

    }

    CharSequence toString(Object obj) {
        checkNotNull(obj);
        return (obj instanceof CharSequence) ? (CharSequence) obj : obj.toString();
    }


    private Iterable<Object> iterable(Object first, Object second, Object[] rest) {
        return new AbstractList<Object>() {
            @Override
            public Object get(int index) {
                switch (index) {
                    case 0:
                        return first;
                    case 1:
                        return second;
                    default:
                        return rest[index - 2];
                }
            }

            @Override
            public int size() {
                return rest.length + 2;
            }
        };
    }


}
