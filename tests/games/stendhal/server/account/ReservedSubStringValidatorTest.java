package games.stendhal.server.account;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ReservedSubStringValidatorTest {

	@Test
	public final void testValidateAdmin() {
		ReservedSubStringValidator rssv = new ReservedSubStringValidator("tadmin");
		assertNotNull(rssv.validate());
		rssv = new ReservedSubStringValidator("admint");
		assertNotNull(rssv.validate());
		rssv = new ReservedSubStringValidator("admin");
		assertNotNull(rssv.validate());
		rssv = new ReservedSubStringValidator("admi");
		assertNull(rssv.validate());
	}


	@Test
	public final void testValidateGm() {
		ReservedSubStringValidator rssv = new ReservedSubStringValidator("gmt");
		assertNotNull("BUG: gm should not be allowed", rssv.validate());

		rssv = new ReservedSubStringValidator("tgm");
		assertNotNull(rssv.validate());
		rssv = new ReservedSubStringValidator("gm");
		assertNotNull(rssv.validate());
		rssv = new ReservedSubStringValidator("egmond");
		assertNotNull(rssv.validate());
	}
}
