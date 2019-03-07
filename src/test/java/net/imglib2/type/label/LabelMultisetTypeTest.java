package net.imglib2.type.label;

import java.util.Iterator;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import net.imglib2.type.label.LabelMultisetType.Entry;

public class LabelMultisetTypeTest
{
	private static final Random rnd = new Random();

	@Test
	public void testInitializationEmpty()
	{
		final LabelMultisetType lmtEmpty = new LabelMultisetType();
		Assert.assertEquals( Label.INVALID, lmtEmpty.argMax() );
		Assert.assertTrue( lmtEmpty.isEmpty() );
	}

	@Test
	public void testInitializationSingleEntry()
	{
		final LabelMultisetType lmtSingleEntry = new LabelMultisetType( new LabelMultisetEntry( 5, 2 ) );
		Assert.assertEquals( 5, lmtSingleEntry.argMax() );
		Assert.assertEquals( 2, lmtSingleEntry.size() );
		Assert.assertEquals( 1, lmtSingleEntry.entrySet().size() );
	}

	@Test
	public void testInitializationMultipleEntries()
	{
		final LabelMultisetEntryList multipleEntries = new LabelMultisetEntryList();
		final int numEntries = rnd.nextInt( 20 );
		for ( int i = 0; i < numEntries; ++i )
			multipleEntries.add( new LabelMultisetEntry( rnd.nextInt( 1000 ), rnd.nextInt( 10 ) ) );
		final LabelMultisetType lmtMultipleEntries = new LabelMultisetType( multipleEntries );
		Assert.assertEquals( numEntries, lmtMultipleEntries.entrySet().size() );
		final Iterator< ? extends Entry< Label > > itExpected = multipleEntries.iterator();
		final Iterator< Entry< Label > > itActual = lmtMultipleEntries.entrySet().iterator();
		while ( itExpected.hasNext() || itActual.hasNext() )
		{
			final Entry< Label > entryExpected = itExpected.next();
			final Entry< Label > entryActual = itActual.next();
			Assert.assertEquals( entryExpected.getElement().id(), entryActual.getElement().id() );
			Assert.assertEquals( entryExpected.getCount(), entryActual.getCount() );
		}
	}
}
