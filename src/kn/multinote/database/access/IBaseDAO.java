package kn.multinote.database.access;

import kn.supportrelax.database.transaction.TransactionCommandAck;

public interface IBaseDAO<Entity, Id> {
	TransactionCommandAck save(Entity entity);

	TransactionCommandAck deleteById(Id id);

	TransactionCommandAck delete(Entity entity);

	TransactionCommandAck getAll();

	TransactionCommandAck getById(Id id);

	void close();
}
