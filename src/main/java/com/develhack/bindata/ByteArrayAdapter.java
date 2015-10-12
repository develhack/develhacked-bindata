package com.develhack.bindata;

import static com.develhack.Conditions.checkInRange;

import java.util.Arrays;

import com.develhack.annotation.assertion.InRange;
import com.develhack.annotation.assertion.Nonempty;
import com.develhack.annotation.assertion.Nonnull;
import com.develhack.annotation.feature.Accessible;

public class ByteArrayAdapter {

	/** reference of the data specified in the constructor */
	@Accessible
	private final byte[] data;

	public ByteArrayAdapter(@Nonnull byte[] data) {
		this.data = data;
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

	public void setByteUnitValue(@Nonempty byte[] value,
 @InRange(from = "0", to = "data.length - value.length") int byteOffset) {

		checkInRange("byteOffset", byteOffset, 0, data.length - value.length);

		System.arraycopy(value, 0, data, byteOffset, value.length);
	}

	public void setByteUnitValue(int value, @InRange(from = "0", to = "data.length - 1") int byteOffset,
			@InRange(from = "1", to = "data.length - byteOffset") int byteLength) {

		checkInRange("byteOffset", byteOffset, 0, data.length - 1);
		checkInRange("byteLength", byteLength, 1, data.length - byteOffset);

		setByteUnitValue0(value, byteOffset, byteLength);
	}

	public void setLittleEndianValue(int value, @InRange(from = "0", to = "data.length - 1") int byteOffset,
			@InRange(from = "1", to = "data.length - byteOffset") int byteLength) {

		checkInRange("byteOffset", byteOffset, 0, data.length - 1);
		checkInRange("byteLength", byteLength, 1, data.length - byteOffset);

		for (int end = byteOffset + byteLength; byteOffset < end; ++byteOffset) {
			data[byteOffset] = (byte) value;
			value >>= Byte.SIZE;
		}
	}

	public void setBitUnitValue(int value, @InRange(from = "0", to = "(data.length * Byte.SIZE) - 1") long bitOffset,
			@InRange(from = "1", to = "(data.length * Byte.SIZE) - bitOffset") long bitLength) {

		checkInRange("bitOffset", bitOffset, 0, (data.length * Byte.SIZE) - 1);
		checkInRange("bitLength", bitLength, 1, (data.length * Byte.SIZE) - bitOffset);

		int byteOffset = (int) (bitOffset >>> 3);
		int bitOffsetInFirstByte = ((int) bitOffset) & 7;

		if ((bitOffsetInFirstByte | ((int) bitLength) & 7) == 0) {
			// mask is unnecessary.
			setByteUnitValue0(value, byteOffset, (int) (bitLength >>> 3));
			return;
		}

		int reverseBitIndexInFirstByte = Byte.SIZE - bitOffsetInFirstByte;
		{
			long reverseBitOffsetInFirstByte = reverseBitIndexInFirstByte - bitLength;
			if (reverseBitOffsetInFirstByte >= 0L) {
				// not cross byte boundary.
				int mask = (-1 >>> -bitLength) << reverseBitOffsetInFirstByte;
				data[byteOffset] = (byte) ((data[byteOffset] & ~mask) | ((value << reverseBitOffsetInFirstByte) & mask));
				return;
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
