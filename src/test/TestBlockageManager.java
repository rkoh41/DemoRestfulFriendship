package test;

import java.sql.SQLException;

import org.junit.Test;

import com.pixelcised.demorestfulfriendship.Model.BlockageManager;
import com.pixelcised.demorestfulfriendship.Model.Entity.Blockage;
import com.pixelcised.demorestfulfriendship.exception.FriendshipException;

public class TestBlockageManager {

	@Test(expected=FriendshipException.class)
	public void testAddBlockageForBlockedUser() throws SQLException, FriendshipException {
		BlockageManager bm = new BlockageManager();
		//the following blockage has already been executed before.
		Blockage bloc = new Blockage("charlie@me.com","delta@me.com");
		try {
			bm.addBlockage(bloc);
		}
		catch(FriendshipException fe) {
			throw fe;
		}
	}
}
