package kn.multinote.factory;

public interface IComponent {
	<T> T getComponent(Class<?> clazz);
}
