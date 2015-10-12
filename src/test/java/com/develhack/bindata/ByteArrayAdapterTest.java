package com.develhack.bindata;

import static org.testng.Assert.assertEquals;

import javax.xml.bind.DatatypeConverter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("javadoc")
public class ByteArrayAdapterTest {

	@Test(dataProviderClass = DataProviders.class, dataProvider = "initialDataAndPositionToGetWithExpectedOfGetByteUnitValue")
	public void getByteUnitValue_GetsTheValueFromSpecifiedPosition(String initial, int offset, int length, String expected) {

		ByteArrayAdapter tested = new ByteArrayAdapter(parse(initial));

		assertEquals(print(tested.getByteUnitValue(offset, length)), expected);
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "initialDataAndPositionToGetWithExpectedOfGetByteUnitValueAsInt")
	public void getByteUnitValueAsInt_GetsTheValueFromSpecifiedPosition(String initial, int offset, int length, int expected) {

		ByteArrayAdapter tested = new ByteArrayAdapter(parse(initial));

		assertEquals(tested.getByteUnitValueAsInt(offset, length), expected);
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "initialDataAndPositionToGetWithExpectedOfGetUnsignedByteUnitValueAsInt")
	public void getUnsignedByteUnitValueAsInt_GetsTheValueFromSpecifiedPosition(String initial, int offset, int length,
			int expected) {

		ByteArrayAdapter tested = new ByteArrayAdapter(parse(initial));

		assertEquals(tested.getUnsignedByteUnitValueAsInt(offset, length), expected);
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "initialDataAndPositionToGetWithExpectedOfGetLittleEndianValueAsInt")
	public void getLittleEndianValueAsInt_GetsTheValueFromSpecifiedPosition(String initial, int offset, int length, int expected) {

		ByteArrayAdapter tested = new ByteArrayAdapter(parse(initial));

		assertEquals(tested.getLittleEndianValueAsInt(offset, length), expected);
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "initialDataAndPositionToGetWithExpectedOfGetUnsignedLittleEndianValueAsInt")
	public void getUnsignedLittleEndianValueAsInt_GetsTheValueFromSpecifiedPosition(String initial, int offset, int length,
			int expected) {

		ByteArrayAdapter tested = new ByteArrayAdapter(parse(initial));

		assertEquals(tested.getUnsignedLittleEndianValueAsInt(offset, length), expected);
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "initialDataAndPositionToGetWithExpectedOfGetBitUnitValueAsInt")
	public void getBitUnitValueAsInt_GetsTheValueFromSpecifiedPosition(String initial, int offset, int length, int expected) {

		ByteArrayAdapter tested = new ByteArrayAdapter(parse(initial));

		assertEquals(tested.getBitUnitValueAsInt(offset, length), expected);
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "initialDataAndPositionToGetWithExpectedOfGetUnsignedBitUnitValueAsInt")
	public void getUnsignedBitUnitValueAsInt_GetsTheValueFromSpecifiedPosition(String initial, int offset, int length,
			int expected) {

		ByteArrayAdapter tested = new ByteArrayAdapter(parse(initial));

		assertEquals(tested.getUnsignedBitUnitValueAsInt(offset, length), expected);
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "initialDataAndValueToBeSetWithExpectedOfSetByteUnitValueTakingByteArrayValue")
	public void setByteUnitValueTakingByteArrayValue_SetsTheValueToSpecifiedPosition(String initial, String value, int offset,
			String expected) {

		ByteArrayAdapter tested = new ByteArrayAdapter(parse(initial));

		tested.setByteUnitValue(parse(value), offset);

		assertEquals(print(tested.getData()), expected);
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "dataSizeAndOutOfRangePosition", expectedExceptions = IllegalArgumentException.class)
	public void setByteUnitValueTakingIntValue_OffsetOrLengthIsOutOfRange_ThrownException(int dataSize, int offset, int length) {

		ByteArrayAdapter tested = new ByteArrayAdapter(new byte[dataSize]);

		tested.setByteUnitValue(0xFF, offset, length);
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "initialDataAndValueToBeSetWithExpectedOfSetByteUnitValueTakingIntValue")
	public void setByteUnitValueTakingIntValue_SetsTheValueToSpecifiedPosition(String initial, int value, int offset,
			int length, String expected) {

		ByteArrayAdapter tested = new ByteArrayAdapter(parse(initial));

		tested.setByteUnitValue(value, offset, length);

		assertEquals(print(tested.getData()), expected);
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "dataSizeAndOutOfRangePosition", expectedExceptions = IllegalArgumentException.class)
	public void setLittleEndianValue_OffsetOrLengthIsOutOfRange_ThrownException(int dataSize, int offset, int length) {

		ByteArrayAdapter tested = new ByteArrayAdapter(new byte[dataSize]);

		tested.setLittleEndianValue(0xFF, offset, length);
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "initialDataAndValueToBeSetWithExpectedOfSetLittleEndianValue")
	public void setLittleEndianValue_SetsTheValueAsLittleEndianToSpecifiedPosition(String initial, int value, int offset,
			int length, String expected) {

		ByteArrayAdapter tested = new ByteArrayAdapter(parse(initial));

		tested.setLittleEndianValue(value, offset, length);

		assertEquals(print(tested.getData()), expected);
	}

	@Test(dataProviderClass = DataProviders.class, dataProvider = "initialDataAndValueToBeSetWithExpectedOfSetBitUnitValue")
	public void setBitUnitValue_SetsTheValueAsLittleEndianToSpecifiedPosition(String initial, int value, int offset,
			int length, String expected) {

		ByteArrayAdapter tested = new ByteArrayAdapter(parse(initial));

		tested.setBitUnitValue(value, offset, length);

		assertEquals(print(tested.getData()), expected);
	}

	private byte[] parse(String hexString) {
		return DatatypeConverter.parseHexBinary(hexString.replaceAll(" ", ""));
	}

	private String print(byte[] data) {
		return DatatypeConverter.printHexBinary(data).toUpperCase().replaceAll("(\\w{2})", "$1 ").trim();
	}

	static class DataProviders {

		@DataProvider
		static Object[][] initialDataAndPositionToGetWithExpectedOfGetByteUnitValue() {
			return new Object[][] {// @formatter:off
					{ "00 AB CD EF 00", 0, 1, "00" },
					{ "00 AB CD EF 00", 0, 2, "00 AB" },
					{ "00 AB CD EF 00", 0, 3, "00 AB CD" },
					{ "00 AB CD EF 00", 0, 4, "00 AB CD EF" },
					{ "00 AB CD EF 00", 0, 5, "00 AB CD EF 00" },
					{ "00 AB CD EF 00", 1, 1, "AB" },
					{ "00 AB CD EF 00", 1, 2, "AB CD" },
					{ "00 AB CD EF 00", 1, 3, "AB CD EF" },
					{ "00 AB CD EF 00", 1, 4, "AB CD EF 00" },
					{ "00 AB CD EF 00", 4, 1, "00" },
			}; // @formatter:on
		}

		@DataProvider
		static Object[][] initialDataAndPositionToGetWithExpectedOfGetByteUnitValueAsInt() {
			return new Object[][] {// @formatter:off
					{ "00 AB CD EF 00", 0, 1, 0x00000000 },
					{ "00 AB CD EF 00", 0, 2, 0x000000AB },
					{ "00 AB CD EF 00", 0, 3, 0x0000ABCD },
					{ "00 AB CD EF 00", 0, 4, 0x00ABCDEF },
					{ "00 AB CD EF 00", 0, 5, 0xABCDEF00 },
					{ "00 AB CD EF 00", 1, 1, 0xFFFFFFAB },
					{ "00 AB CD EF 00", 1, 2, 0xFFFFABCD },
					{ "00 AB CD EF 00", 1, 3, 0xFFABCDEF },
					{ "00 AB CD EF 00", 1, 4, 0xABCDEF00 },
					{ "00 AB CD EF 00", 4, 1, 0x00000000 },
					{ "FF AB CD EF FF", 0, 1, 0xFFFFFFFF },
					{ "FF AB CD EF FF", 0, 2, 0xFFFFFFAB },
					{ "FF AB CD EF FF", 0, 3, 0xFFFFABCD },
					{ "FF AB CD EF FF", 0, 4, 0xFFABCDEF },
					{ "FF AB CD EF FF", 0, 5, 0xABCDEFFF },
					{ "FF AB CD EF FF", 1, 1, 0xFFFFFFAB },
					{ "FF AB CD EF FF", 1, 2, 0xFFFFABCD },
					{ "FF AB CD EF FF", 1, 3, 0xFFABCDEF },
					{ "FF AB CD EF FF", 1, 4, 0xABCDEFFF },
					{ "FF AB CD EF FF", 4, 1, 0xFFFFFFFF },
			}; // @formatter:on
		}

		@DataProvider
		static Object[][] initialDataAndPositionToGetWithExpectedOfGetUnsignedByteUnitValueAsInt() {
			return new Object[][] {// @formatter:off
					{ "00 AB CD EF 00", 0, 1, 0x00000000 },
					{ "00 AB CD EF 00", 0, 2, 0x000000AB },
					{ "00 AB CD EF 00", 0, 3, 0x0000ABCD },
					{ "00 AB CD EF 00", 0, 4, 0x00ABCDEF },
					{ "00 AB CD EF 00", 0, 5, 0xABCDEF00 },
					{ "00 AB CD EF 00", 1, 1, 0x000000AB },
					{ "00 AB CD EF 00", 1, 2, 0x0000ABCD },
					{ "00 AB CD EF 00", 1, 3, 0x00ABCDEF },
					{ "00 AB CD EF 00", 1, 4, 0xABCDEF00 },
					{ "00 AB CD EF 00", 4, 1, 0x00000000 },
					{ "FF AB CD EF FF", 0, 1, 0x000000FF },
					{ "FF AB CD EF FF", 0, 2, 0x0000FFAB },
					{ "FF AB CD EF FF", 0, 3, 0x00FFABCD },
					{ "FF AB CD EF FF", 0, 4, 0xFFABCDEF },
					{ "FF AB CD EF FF", 0, 5, 0xABCDEFFF },
					{ "FF AB CD EF FF", 1, 1, 0x000000AB },
					{ "FF AB CD EF FF", 1, 2, 0x0000ABCD },
					{ "FF AB CD EF FF", 1, 3, 0x00ABCDEF },
					{ "FF AB CD EF FF", 1, 4, 0xABCDEFFF },
					{ "FF AB CD EF FF", 4, 1, 0x000000FF },
			}; // @formatter:on
		}

		@DataProvider
		static Object[][] initialDataAndPositionToGetWithExpectedOfGetLittleEndianValueAsInt() {
			return new Object[][] {// @formatter:off
					{ "00 AB CD EF 00", 0, 1, 0x00000000 },
					{ "00 AB CD EF 00", 0, 2, 0xFFFFAB00 },
					{ "00 AB CD EF 00", 0, 3, 0xFFCDAB00 },
					{ "00 AB CD EF 00", 0, 4, 0xEFCDAB00 },
					{ "00 AB CD EF 00", 0, 5, 0xEFCDAB00 },
					{ "00 AB CD EF 00", 1, 1, 0xFFFFFFAB },
					{ "00 AB CD EF 00", 1, 2, 0xFFFFCDAB },
					{ "00 AB CD EF 00", 1, 3, 0xFFEFCDAB },
					{ "00 AB CD EF 00", 1, 4, 0x00EFCDAB },
					{ "00 AB CD EF 00", 4, 1, 0x00000000 },
					{ "FF AB CD EF FF", 0, 1, 0xFFFFFFFF },
					{ "FF AB CD EF FF", 0, 2, 0xFFFFABFF },
					{ "FF AB CD EF FF", 0, 3, 0xFFCDABFF },
					{ "FF AB CD EF FF", 0, 4, 0xEFCDABFF },
					{ "FF AB CD EF FF", 0, 5, 0xEFCDABFF },
					{ "FF AB CD EF FF", 1, 1, 0xFFFFFFAB },
					{ "FF AB CD EF FF", 1, 2, 0xFFFFCDAB },
					{ "FF AB CD EF FF", 1, 3, 0xFFEFCDAB },
					{ "FF AB CD EF FF", 1, 4, 0xFFEFCDAB },
					{ "FF AB CD EF FF", 4, 1, 0xFFFFFFFF },
			}; // @formatter:on
		}

		@DataProvider
		static Object[][] initialDataAndPositionToGetWithExpectedOfGetUnsignedLittleEndianValueAsInt() {
			return new Object[][] {// @formatter:off
					{ "00 AB CD EF 00", 0, 1, 0x00000000 },
					{ "00 AB CD EF 00", 0, 2, 0x0000AB00 },
					{ "00 AB CD EF 00", 0, 3, 0x00CDAB00 },
					{ "00 AB CD EF 00", 0, 4, 0xEFCDAB00 },
					{ "00 AB CD EF 00", 0, 5, 0xEFCDAB00 },
					{ "00 AB CD EF 00", 1, 1, 0x000000AB },
					{ "00 AB CD EF 00", 1, 2, 0x0000CDAB },
					{ "00 AB CD EF 00", 1, 3, 0x00EFCDAB },
					{ "00 AB CD EF 00", 1, 4, 0x00EFCDAB },
					{ "00 AB CD EF 00", 4, 1, 0x00000000 },
					{ "FF AB CD EF FF", 0, 1, 0x000000FF },
					{ "FF AB CD EF FF", 0, 2, 0x0000ABFF },
					{ "FF AB CD EF FF", 0, 3, 0x00CDABFF },
					{ "FF AB CD EF FF", 0, 4, 0xEFCDABFF },
					{ "FF AB CD EF FF", 0, 5, 0xEFCDABFF },
					{ "FF AB CD EF FF", 1, 1, 0x000000AB },
					{ "FF AB CD EF FF", 1, 2, 0x0000CDAB },
					{ "FF AB CD EF FF", 1, 3, 0x00EFCDAB },
					{ "FF AB CD EF FF", 1, 4, 0xFFEFCDAB },
					{ "FF AB CD EF FF", 4, 1, 0x000000FF },
			}; // @formatter:on
		}

		@DataProvider
		static Object[][] initialDataAndPositionToGetWithExpectedOfGetBitUnitValueAsInt() {
			return new Object[][] {// @formatter:off
					{ "09 AB CD EF FF",  0,  2, 0x00000000 },
					{ "09 AB CD EF FF",  0,  4, 0x00000000 },
					{ "09 AB CD EF FF",  0,  6, 0x00000002 },
					{ "09 AB CD EF FF",  0,  8, 0x00000009 },
					{ "09 AB CD EF FF",  0, 16, 0x000009AB },
					{ "09 AB CD EF FF",  2,  2, 0x00000000 },
					{ "09 AB CD EF FF",  2,  4, 0x00000002 },
					{ "09 AB CD EF FF",  2,  6, 0x00000009 },
					{ "09 AB CD EF FF",  2,  8, 0x00000026 },
					{ "09 AB CD EF FF",  2, 16, 0x000026AF },
					{ "09 AB CD EF FF",  4,  2, 0xFFFFFFFE },
					{ "09 AB CD EF FF",  4,  4, 0xFFFFFFF9 },
					{ "09 AB CD EF FF",  4,  6, 0xFFFFFFE6 },
					{ "09 AB CD EF FF",  4,  8, 0xFFFFFF9A },
					{ "09 AB CD EF FF",  4, 16, 0xFFFF9ABC },
					{ "09 AB CD EF FF",  8,  2, 0xFFFFFFFE },
					{ "09 AB CD EF FF",  8,  4, 0xFFFFFFFA },
					{ "09 AB CD EF FF",  8,  6, 0xFFFFFFEA },
					{ "09 AB CD EF FF",  8,  8, 0xFFFFFFAB },
					{ "09 AB CD EF FF",  8, 16, 0xFFFFABCD },
					{ "09 AB CD EF FF", 16,  2, 0xFFFFFFFF },
					{ "09 AB CD EF FF", 16,  4, 0xFFFFFFFC },
					{ "09 AB CD EF FF", 16,  6, 0xFFFFFFF3 },
					{ "09 AB CD EF FF", 16,  8, 0xFFFFFFCD },
					{ "09 AB CD EF FF", 16, 16, 0xFFFFCDEF },
			}; // @formatter:on
		}

		@DataProvider
		static Object[][] initialDataAndPositionToGetWithExpectedOfGetUnsignedBitUnitValueAsInt() {
			return new Object[][] {// @formatter:off
					{ "09 AB CD EF FF",  0,  2, 0x00000000 },
					{ "09 AB CD EF FF",  0,  4, 0x00000000 },
					{ "09 AB CD EF FF",  0,  6, 0x00000002 },
					{ "09 AB CD EF FF",  0,  8, 0x00000009 },
					{ "09 AB CD EF FF",  0, 16, 0x000009AB },
					{ "09 AB CD EF FF",  2,  2, 0x00000000 },
					{ "09 AB CD EF FF",  2,  4, 0x00000002 },
					{ "09 AB CD EF FF",  2,  6, 0x00000009 },
					{ "09 AB CD EF FF",  2,  8, 0x00000026 },
					{ "09 AB CD EF FF",  2, 16, 0x000026AF },
					{ "09 AB CD EF FF",  4,  2, 0x00000002 },
					{ "09 AB CD EF FF",  4,  4, 0x00000009 },
					{ "09 AB CD EF FF",  4,  6, 0x00000026 },
					{ "09 AB CD EF FF",  4,  8, 0x0000009A },
					{ "09 AB CD EF FF",  4, 16, 0x00009ABC },
					{ "09 AB CD EF FF",  8,  2, 0x00000002 },
					{ "09 AB CD EF FF",  8,  4, 0x0000000A },
					{ "09 AB CD EF FF",  8,  6, 0x0000002A },
					{ "09 AB CD EF FF",  8,  8, 0x000000AB },
					{ "09 AB CD EF FF",  8, 16, 0x0000ABCD },
					{ "09 AB CD EF FF", 16,  2, 0x00000003 },
					{ "09 AB CD EF FF", 16,  4, 0x0000000C },
					{ "09 AB CD EF FF", 16,  6, 0x00000033 },
					{ "09 AB CD EF FF", 16,  8, 0x000000CD },
					{ "09 AB CD EF FF", 16, 16, 0x0000CDEF },
			}; // @formatter:on
		}

		@DataProvider
		static Object[][] dataSizeAndOutOfRangePosition() {
			return new Object[][] {// @formatter:off
					{ 0,  0,  0 },
					{ 1, -1,  0 },
					{ 1, -1,  1 },
					{ 1,  0, -1 },
					{ 1,  0,  0 },
					{ 1,  0,  2 },
					{ 1,  1, -1 },
					{ 1,  1,  0 },
					{ 1,  1,  1 },
					{ 2,  2,  0 },
					{ 2,  0,  3 },
					{ 2,  1,  2 },
			}; // @formatter:on
		}

		@DataProvider
		static Object[][] initialDataAndValueToBeSetWithExpectedOfSetByteUnitValueTakingByteArrayValue() {
			return new Object[][] {// @formatter:off
					{ "00 00 00 00 00", "FF FF", 0, "FF FF 00 00 00" },
					{ "00 00 00 00 00", "FF FF", 1, "00 FF FF 00 00" },
					{ "00 00 00 00 00", "FF FF", 2, "00 00 FF FF 00" },
					{ "00 00 00 00 00", "FF FF", 3, "00 00 00 FF FF" },
			}; // @formatter:on
		}

		@DataProvider
		static Object[][] initialDataAndValueToBeSetWithExpectedOfSetByteUnitValueTakingIntValue() {
			return new Object[][] {// @formatter:off
					{ "00 00 00 00 00", 0xABCD, 0, 1, "CD 00 00 00 00" },
					{ "00 00 00 00 00", 0xABCD, 1, 1, "00 CD 00 00 00" },
					{ "00 00 00 00 00", 0xABCD, 2, 1, "00 00 CD 00 00" },
					{ "00 00 00 00 00", 0xABCD, 3, 1, "00 00 00 CD 00" },
					{ "00 00 00 00 00", 0xABCD, 4, 1, "00 00 00 00 CD" },
					{ "00 00 00 00 00", 0xABCD, 0, 2, "AB CD 00 00 00" },
					{ "00 00 00 00 00", 0xABCD, 1, 2, "00 AB CD 00 00" },
					{ "00 00 00 00 00", 0xABCD, 2, 2, "00 00 AB CD 00" },
					{ "00 00 00 00 00", 0xABCD, 3, 2, "00 00 00 AB CD" },
					{ "00 00 00 00 00", 0xABCD, 0, 3, "00 AB CD 00 00" },
					{ "00 00 00 00 00", 0xABCD, 1, 3, "00 00 AB CD 00" },
					{ "00 00 00 00 00", 0xABCD, 2, 3, "00 00 00 AB CD" },
					{ "00 00 00 00 00", 0xABCD, 0, 5, "00 00 00 AB CD" },
					{ "FF FF FF FF FF", 0xABCD, 0, 1, "CD FF FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 1, 1, "FF CD FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 2, 1, "FF FF CD FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 3, 1, "FF FF FF CD FF" },
					{ "FF FF FF FF FF", 0xABCD, 4, 1, "FF FF FF FF CD" },
					{ "FF FF FF FF FF", 0xABCD, 0, 2, "AB CD FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 1, 2, "FF AB CD FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 2, 2, "FF FF AB CD FF" },
					{ "FF FF FF FF FF", 0xABCD, 3, 2, "FF FF FF AB CD" },
					{ "FF FF FF FF FF", 0xABCD, 0, 3, "00 AB CD FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 1, 3, "FF 00 AB CD FF" },
					{ "FF FF FF FF FF", 0xABCD, 2, 3, "FF FF 00 AB CD" },
					{ "FF FF FF FF FF", 0xABCD, 0, 5, "00 00 00 AB CD" },
			}; // @formatter:on
		}

		@DataProvider
		static Object[][] initialDataAndValueToBeSetWithExpectedOfSetLittleEndianValue() {
			return new Object[][] {// @formatter:off
					{ "00 00 00 00 00", 0xABCD, 0, 1, "CD 00 00 00 00" },
					{ "00 00 00 00 00", 0xABCD, 1, 1, "00 CD 00 00 00" },
					{ "00 00 00 00 00", 0xABCD, 2, 1, "00 00 CD 00 00" },
					{ "00 00 00 00 00", 0xABCD, 3, 1, "00 00 00 CD 00" },
					{ "00 00 00 00 00", 0xABCD, 4, 1, "00 00 00 00 CD" },
					{ "00 00 00 00 00", 0xABCD, 0, 2, "CD AB 00 00 00" },
					{ "00 00 00 00 00", 0xABCD, 1, 2, "00 CD AB 00 00" },
					{ "00 00 00 00 00", 0xABCD, 2, 2, "00 00 CD AB 00" },
					{ "00 00 00 00 00", 0xABCD, 3, 2, "00 00 00 CD AB" },
					{ "00 00 00 00 00", 0xABCD, 0, 3, "CD AB 00 00 00" },
					{ "00 00 00 00 00", 0xABCD, 1, 3, "00 CD AB 00 00" },
					{ "00 00 00 00 00", 0xABCD, 2, 3, "00 00 CD AB 00" },
					{ "00 00 00 00 00", 0xABCD, 0, 5, "CD AB 00 00 00" },
					{ "FF FF FF FF FF", 0xABCD, 0, 1, "CD FF FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 1, 1, "FF CD FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 2, 1, "FF FF CD FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 3, 1, "FF FF FF CD FF" },
					{ "FF FF FF FF FF", 0xABCD, 4, 1, "FF FF FF FF CD" },
					{ "FF FF FF FF FF", 0xABCD, 0, 2, "CD AB FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 1, 2, "FF CD AB FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 2, 2, "FF FF CD AB FF" },
					{ "FF FF FF FF FF", 0xABCD, 3, 2, "FF FF FF CD AB" },
					{ "FF FF FF FF FF", 0xABCD, 0, 3, "CD AB 00 FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 1, 3, "FF CD AB 00 FF" },
					{ "FF FF FF FF FF", 0xABCD, 2, 3, "FF FF CD AB 00" },
					{ "FF FF FF FF FF", 0xABCD, 0, 5, "CD AB 00 00 00" },
			}; // @formatter:on
		}

		@DataProvider
		static Object[][] initialDataAndValueToBeSetWithExpectedOfSetBitUnitValue() {
			return new Object[][] {// @formatter:off
					{ "00 00 00 00 00", 0xABCD,  0,  2, "40 00 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  0,  4, "D0 00 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  0,  6, "34 00 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  0,  8, "CD 00 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  0, 16, "AB CD 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  2,  2, "10 00 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  2,  4, "34 00 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  2,  6, "0D 00 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  2,  8, "33 40 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  2, 16, "2A F3 40 00 00" },
					{ "00 00 00 00 00", 0xABCD,  4,  2, "04 00 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  4,  4, "0D 00 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  4,  6, "03 40 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  4,  8, "0C D0 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  4, 16, "0A BC D0 00 00" },
					{ "00 00 00 00 00", 0xABCD,  8,  2, "00 40 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  8,  4, "00 D0 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  8,  6, "00 34 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  8,  8, "00 CD 00 00 00" },
					{ "00 00 00 00 00", 0xABCD,  8, 16, "00 AB CD 00 00" },
					{ "00 00 00 00 00", 0xABCD, 16,  2, "00 00 40 00 00" },
					{ "00 00 00 00 00", 0xABCD, 16,  4, "00 00 D0 00 00" },
					{ "00 00 00 00 00", 0xABCD, 16,  6, "00 00 34 00 00" },
					{ "00 00 00 00 00", 0xABCD, 16,  8, "00 00 CD 00 00" },
					{ "00 00 00 00 00", 0xABCD, 16, 16, "00 00 AB CD 00" },
					{ "FF FF FF FF FF", 0xABCD,  0,  2, "7F FF FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  0,  4, "DF FF FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  0,  6, "37 FF FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  0,  8, "CD FF FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  0, 16, "AB CD FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  2,  2, "DF FF FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  2,  4, "F7 FF FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  2,  6, "CD FF FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  2,  8, "F3 7F FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  2, 16, "EA F3 7F FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  4,  2, "F7 FF FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  4,  4, "FD FF FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  4,  6, "F3 7F FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  4,  8, "FC DF FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  4, 16, "FA BC DF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  8,  2, "FF 7F FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  8,  4, "FF DF FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  8,  6, "FF 37 FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  8,  8, "FF CD FF FF FF" },
					{ "FF FF FF FF FF", 0xABCD,  8, 16, "FF AB CD FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 16,  2, "FF FF 7F FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 16,  4, "FF FF DF FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 16,  6, "FF FF 37 FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 16,  8, "FF FF CD FF FF" },
					{ "FF FF FF FF FF", 0xABCD, 16, 16, "FF FF AB CD FF" },
			}; // @formatter:on
		}
	}
}
