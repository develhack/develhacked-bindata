package com.develhack.bindata;

import static com.develhack.Conditions.checkInRange;

import java.util.Arrays;

import com.develhack.annotation.assertion.InRange;
import com.develhack.annotation.assertion.Nonempty;
import com.develhack.annotation.assertion.Nonnull;
import com.develhack.annotation.assertion.Positive;
import com.develhack.annotation.feature.Accessible;

public class ByteArrayAdapter {

	private static final char[] LOWER_DIGITS = "0123456789abcdef".toCharArray();
	private static final char[] UPPSER_DIGITS = "0123456789ABCDEF".toCharArray();

	public static String toString(byte[] data) {
		return toString(data, (char) 0, false);
	}

	public static String toString(byte[] data, char delimiter, boolean upperDigits) {

		if (data == null) return null;
		if (data.length == 0) return "";

		char[] digits = upperDigits ? UPPSER_DIGITS : LOWER_DIGITS;

		if (delimiter == 0) {

			StringBuilder buff = new StringBuilder((data.length * 2));
			for (byte b : data) {
				buff.append(digits[(b >>> 4) & 0xF]);
				buff.append(digits[b & 0xF]);
			}

			return buff.toString();
		}

		StringBuilder buff = new StringBuilder((data.length * 3));
		for (byte b : data) {
			buff.append(digits[b >>> 4]);
			buff.append(digits[b & 0xF]);
			buff.append(delimiter);
		}
		buff.setLength(buff.length() - 1);

		return buff.toString();
	}

	/** reference of the data specified in the constructor */
	@Accessible
	private final byte[] data;

	public ByteArrayAdapter(@Positive int size) {
		this.data = new byte[size];
	}

	public ByteArrayAdapter(@Nonnull byte[] data) {
		this.data = data;
	}

	public String getHexString(@InRange(from = "0", to = "data.length - 1") int byteOffset,
			@InRange(from = "1", to = "data.length - byteOffset") int byteLength) {

		checkInRange("byteOffset", byteOffset, 0, data.length - 1);
		checkInRange("byteLength", byteLength, 1, data.length - byteOffset);

		return toString(getByteUnitValue(byteOffset, byteLength));
	}

	public byte[] getByteUnitValue(@InRange(from = "0", to = "data.length - 1") int byteOffset,
			@InRange(from = "1", to = "data.length - byteOffset") int byteLength) {

		checkInRange("byteOffset", byteOffset, 0, data.length - 1);
		checkInRange("byteLength", byteLength, 1, data.length - byteOffset);

		return Arrays.copyOfRange(data, byteOffset, byteOffset + byteLength);
	}

	public int getByteUnitValueAsInt(@InRange(from = "0", to = "data.length - 1") int byteOffset,
			@InRange(from = "1", to = "data.length - byteOffset") int byteLength) {

		checkInRange("byteOffset", byteOffset, 0, data.length - 1);
		checkInRange("byteLength", byteLength, 1, data.length - byteOffset);

		return getByteUnitValueAsInt(byteOffset, byteLength, false);
	}

	public int getUnsignedByteUnitValueAsInt(@InRange(from = "0", to = "data.length - 1") int byteOffset,
			@InRange(from = "1", to = "data.length - byteOffset") int byteLength) {

		checkInRange("byteOffset", byteOffset, 0, data.length - 1);
		checkInRange("byteLength", byteLength, 1, data.length - byteOffset);

		return getByteUnitValueAsInt(byteOffset, byteLength, true);
	}

	public int getLittleEndianValueAsInt(@InRange(from = "0", to = "data.length - 1") int byteOffset,
			@InRange(from = "1", to = "data.length - byteOffset") int byteLength) {

		checkInRange("byteOffset", byteOffset, 0, data.length - 1);
		checkInRange("byteLength", byteLength, 1, data.length - byteOffset);

		return getLittleEndianValueAsInt(byteOffset, byteLength, false);
	}

	public int getUnsignedLittleEndianValueAsInt(@InRange(from = "0", to = "data.length - 1") int byteOffset,
			@InRange(from = "1", to = "data.length - byteOffset") int byteLength) {

		checkInRange("byteOffset", byteOffset, 0, data.length - 1);
		checkInRange("byteLength", byteLength, 1, data.length - byteOffset);

		return getLittleEndianValueAsInt(byteOffset, byteLength, true);
	}

	public int getBitUnitValueAsInt(@InRange(from = "0", to = "(data.length * Byte.SIZE) - 1") long bitOffset,
			@InRange(from = "1", to = "(data.length * Byte.SIZE) - bitOffset") long bitLength) {

		checkInRange("bitOffset", bitOffset, 0, (data.length * Byte.SIZE) - 1);
		checkInRange("bitLength", bitLength, 1, (data.length * Byte.SIZE) - bitOffset);

		return getBitUnitValueAsInt(bitOffset, bitLength, false);
	}

	public int getUnsignedBitUnitValueAsInt(@InRange(from = "0", to = "(data.length * Byte.SIZE) - 1") long bitOffset,
			@InRange(from = "1", to = "(data.length * Byte.SIZE) - bitOffset") long bitLength) {

		checkInRange("bitOffset", bitOffset, 0, (data.length * Byte.SIZE) - 1);
		checkInRange("bitLength", bitLength, 1, (data.length * Byte.SIZE) - bitOffset);

		int byteOffset = (int) (bitOffset >>> 3);
		int bitOffsetInFirstByte = ((int) bitOffset) & 7;

		return getBitUnitValueAsInt(bitOffset, bitLength, true);
	}

	public ByteArrayAdapter setByteUnitValue(@Nonempty byte[] value,
			@InRange(from = "0", to = "data.length - value.length") int byteOffset) {

		checkInRange("byteOffset", byteOffset, 0, data.length - value.length);

		System.arraycopy(value, 0, data, byteOffset, value.length);

		return this;
	}

	public ByteArrayAdapter setByteUnitValue(int value, @InRange(from = "0", to = "data.length - 1") int byteOffset,
			@InRange(from = "1", to = "data.length - byteOffset") int byteLength) {

		checkInRange("byteOffset", byteOffset, 0, data.length - 1);
		checkInRange("byteLength", byteLength, 1, data.length - byteOffset);

		setByteUnitValue0(value, byteOffset, byteLength);

		return this;
	}

	public ByteArrayAdapter setLittleEndianValue(int value, @InRange(from = "0", to = "data.length - 1") int byteOffset,
			@InRange(from = "1", to = "data.length - byteOffset") int byteLength) {

		checkInRange("byteOffset", byteOffset, 0, data.length - 1);
		checkInRange("byteLength", byteLength, 1, data.length - byteOffset);

		for (int end = byteOffset + byteLength; byteOffset < end; ++byteOffset) {
			data[byteOffset] = (byte) value;
			value >>= Byte.SIZE;
		}

		return this;
	}

	public ByteArrayAdapter setBitUnitValue(int value,
			@InRange(from = "0", to = "(data.length * Byte.SIZE) - 1") long bitOffset,
			@InRange(from = "1", to = "(data.length * Byte.SIZE) - bitOffset") long bitLength) {

		checkInRange("bitOffset", bitOffset, 0, (data.length * Byte.SIZE) - 1);
		checkInRange("bitLength", bitLength, 1, (data.length * Byte.SIZE) - bitOffset);

		int byteOffset = (int) (bitOffset >>> 3);
		int bitOffsetInFirstByte = ((int) bitOffset) & 7;

		if ((bitOffsetInFirstByte | ((int) bitLength) & 7) == 0) {
			// mask is unnecessary.
			setByteUnitValue0(value, byteOffset, (int) (bitLength >>> 3));
			return this;
		}

		int reverseBitIndexInFirstByte = Byte.SIZE - bitOffsetInFirstByte;
		{
			long reverseBitOffsetInFirstByte = reverseBitIndexInFirstByte - bitLength;
			if (reverseBitOffsetInFirstByte >= 0L) {
				// not cross byte boundary.
				int mask = (-1 >>> -bitLength) << reverseBitOffsetInFirstByte;
				data[byteOffset] = (byte) ((data[byteOffset] & ~mask) | ((value << reverseBitOffsetInFirstByte) & mask));
				return this;
			}
		}

		// cross byte boundary.
		int lastByteIndex = (int) ((bitOffset + bitLength) >>> 3);
		{
			// process last byte.
			int bitLengthInLastByte = (int) ((bitOffset + bitLength) & 7);
			int reverseBitOffsetInLastByte = Byte.SIZE - bitLengthInLastByte;
			int mask = (-1 >>> -bitLengthInLastByte) << reverseBitOffsetInLastByte;
			data[lastByteIndex] = (byte) ((data[lastByteIndex] & ~mask) | ((value << reverseBitOffsetInLastByte) & mask));
			value >>= bitLengthInLastByte;
		}
		{
			// process intervening bytes.
			for (int i = lastByteIndex - 1; i > byteOffset; --i) {
				data[i] = (byte) value;
				value >>= Byte.SIZE;
			}
		}
		{
			// process first byte.
			int mask = (-1 >>> -reverseBitIndexInFirstByte);
			data[byteOffset] = (byte) ((data[byteOffset] & ~mask) | (value & mask));
		}

		return this;
	}

	@Override
	public String toString() {
		return toString(data);
	}

	public String toString(char delimiter, boolean upperDigits) {
		return toString(data, delimiter, upperDigits);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(data);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		return Arrays.equals(data, ((ByteArrayAdapter) obj).data);
	}

	private int getByteUnitValueAsInt(int byteOffset, int byteLength, boolean unsigned) {

		int result = unsigned ? (data[byteOffset++] & 0xFF) : data[byteOffset++];

		for (int end = byteOffset + byteLength - 1; byteOffset < end; ++byteOffset) {
			result <<= Byte.SIZE;
			result |= (data[byteOffset] & 0xFF);
		}

		return result;
	}

	private int getLittleEndianValueAsInt(int byteOffset, int byteLength, boolean unsigned) {

		int i = byteOffset + byteLength - 1;
		int result = unsigned ? (data[i--] & 0xFF) : data[i--];

		while (i >= byteOffset) {
			result <<= Byte.SIZE;
			result |= (data[i--] & 0xFF);
		}

		return result;
	}

	private int getBitUnitValueAsInt(long bitOffset, long bitLength, boolean unsigned) {

		int byteOffset = (int) (bitOffset >>> 3);
		int bitOffsetInFirstByte = ((int) bitOffset) & 7;

		if ((bitOffsetInFirstByte | ((int) bitLength) & 7) == 0) {
			// mask is unnecessary.
			return getByteUnitValueAsInt(byteOffset, (int) (bitLength >>> 3), unsigned);
		}

		int reverseBitIndexInFirstByte = Byte.SIZE - bitOffsetInFirstByte;
		{
			long reverseBitOffsetInFirstByte = reverseBitIndexInFirstByte - bitLength;
			if (reverseBitOffsetInFirstByte >= 0L) {
				// not cross byte boundary.
				int mask = (-1 >>> -bitLength);
				int result = ((data[byteOffset] >> reverseBitOffsetInFirstByte) & mask);
				if (!unsigned && ((1 << (bitLength - 1)) & result) != 0) {
					result |= (-1 << bitLength);
				}
				return result;
			}
		}

		// cross byte boundary.
		int result;
		{
			// process first byte.
			int mask = (-1 >>> -reverseBitIndexInFirstByte);
			result = (data[byteOffset++] & mask);
			if (!unsigned && ((1 << (reverseBitIndexInFirstByte - 1)) & result) != 0) {
				result |= (-1 << reverseBitIndexInFirstByte);
			}
		}
		int lastByteIndex = (int) ((bitOffset + bitLength) >>> 3);
		{
			// process intervening bytes.
			while (byteOffset < lastByteIndex) {
				result <<= Byte.SIZE;
				result |= (data[byteOffset++] & 0xFF);
			}
		}
		{
			// process last byte.
			int bitLengthInLastByte = (int) ((bitOffset + bitLength) & 7);
			int reverseBitOffsetInLastByte = Byte.SIZE - bitLengthInLastByte;
			int mask = (-1 >>> -bitLengthInLastByte);
			result <<= bitLengthInLastByte;
			result |= ((data[lastByteIndex] >>> reverseBitOffsetInLastByte) & mask);
		}

		return result;
	}

	private void setByteUnitValue0(int value, int byteOffset, int byteLength) {

		for (int i = byteOffset + byteLength - 1; i >= byteOffset; --i) {
			data[i] = (byte) value;
			value >>= Byte.SIZE;
		}
	}

}
