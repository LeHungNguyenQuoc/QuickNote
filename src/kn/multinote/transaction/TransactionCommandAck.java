package kn.multinote.transaction;

import java.util.Date;

public class TransactionCommandAck extends TransactionAck<ReturnUpdatedInfo> {
	public TransactionCommandAck() {
		this.returnObject = new ReturnUpdatedInfo();
		this.returnObject.updatedTimestamp = new Date(
				System.currentTimeMillis());
		this.returnObject.recordAffected = -1;
	}
}
