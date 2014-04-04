package kn.multinote.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import kn.multinote.database.access.IBaseDAO;
import kn.multinote.database.access.INoteDAO;
import kn.multinote.database.access.ISystemSettingDAO;

public class DAOFactory implements IComponent {

	private static DAOFactory instance = null;

	@Override
	public <T> T getComponent(Class<?> clazz) {
		try {
			if (clazz.isInterface()) {
				if (clazz.getName().equalsIgnoreCase(
						ISystemSettingDAO.class.getName())) {
					Class<? extends IBaseDAO> claz = Class
							.forName(
									"kn.multinote.database.access.sqlite.SqliteSystemSettingDao")
							.asSubclass(IBaseDAO.class);

					Constructor<?> constr = claz.getConstructor();
					return (T) constr.newInstance();
				} else if (clazz.getName().equalsIgnoreCase(
						INoteDAO.class.getName())) {
					Class<? extends IBaseDAO> claz = Class
							.forName(
									"kn.multinote.database.access.sqlite.SqliteNoteDao")
							.asSubclass(IBaseDAO.class);

					Constructor<?> constr = claz.getConstructor();
					return (T) constr.newInstance();

				}
			} else {
				Class<? extends IBaseDAO> claz = Class.forName(clazz.getName())
						.asSubclass(IBaseDAO.class);

				Constructor<?> constr = claz.getConstructor();
				return (T) constr.newInstance();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static DAOFactory getInstance() {
		if (instance == null) {
			instance = new DAOFactory();
		}
		return instance;
	}
}
