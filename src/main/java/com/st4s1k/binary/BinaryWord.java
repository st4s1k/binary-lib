package com.st4s1k.binary;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class BinaryWord extends Number
{
    private final byte[] word;
    private int size;

    public BinaryWord()
    {
        size = 2;
        word = new byte[size];
    }

    public BinaryWord(int number)
    {
        size = wordSizeInBits(number) / 8;
        word = BigInteger
                .valueOf(number)
                .toByteArray();
    }

//    public BinaryWord(int size)
//    {
//        this.size = size;
//        word = new byte[2 * size];
//    }

    public BinaryWord(byte[] word)
    {
        this.size = word.length;
        this.word = word;
    }

    public static int wordSizeInBits(int number) {
        return (int) Math.ceil(log2(number));
    }

    public static float log2(int x)
    {
        return (float) (Math.log(x) / Math.log(2));
    }

    public static int bitCount(int byteCount)
    {
        return byteCount * 8;
    }

    public static int byteCount(int bitCount)
    {
        return bitCount / 8;
    }

    public int getSize()
    {
        return size;
    }

    public int getByteIndex(int bitPos)
    {
        if (byteCount(bitPos) >= size)
        {
            throw new IndexOutOfBoundsException();
        }

        return byteCount(bitPos);
    }

    public BinaryWord not()
    {
        byte[] newWord = word.clone();

        for (int i = 0; i < newWord.length; i++)
        {
            newWord[i] = (byte) ~newWord[i];
        }

        return new BinaryWord();
    }

    public BinaryWord and(BinaryWord b)
    {
        int minSize = Math.min(this.size, b.size);
        byte[] newWord = new byte[Math.max(this.size, b.size)];

        for (int i = 0; i < minSize; i++)
        {
            newWord[i] = (byte) (this.word[i] & b.word[i]);
        }

        return new BinaryWord(newWord);
    }

    public BinaryWord or(BinaryWord b)
    {
        int minSize = Math.min(this.size, b.size);
        int maxSize = Math.max(this.size, b.size);

        byte[] newWord = new byte[maxSize];

        for (int i = 0; i < minSize; i++)
        {
            newWord[i] = (byte) (this.word[i] | b.word[i]);
        }

        return new BinaryWord(newWord);
    }

    public BinaryWord xor(BinaryWord b)
    {
        int minSize = Math.min(this.size, b.size);
        byte[] newWord = new byte[Math.max(this.size, b.size)];

        for (int i = 0; i < minSize; i++)
        {
            newWord[i] = (byte) (this.word[i] ^ b.word[i]);
        }

        return new BinaryWord(newWord);
    }

    public BinaryWord nand(BinaryWord b)
    {
        return this.and(b).not();
    }

    public void setBit(int bitPos)
    {
        int byteIndex = this.getByteIndex(bitPos);
        this.word[byteIndex] |= 1 << (bitPos - bitCount(byteIndex));
    }

    public void clearBit(int bitPos)
    {
        int byteIndex = this.getByteIndex(bitPos);
        this.word[byteIndex] &= ~(1 << (bitPos - bitCount(byteIndex)));
    }

    public void toggleBit(int bitPos)
    {
        int byteIndex = this.getByteIndex(bitPos);
        this.word[byteIndex] ^= 1 << (bitPos - bitCount(byteIndex));
    }

    public int getBit(int bitPos)
    {
        int byteIndex = this.getByteIndex(bitPos);
        int bitState = (this.word[byteIndex] >> bitPos) & 1;
        return bitState;
    }

    @Override
    public int intValue()
    {
        return ByteBuffer.wrap(word).getInt();
    }

    @Override
    public long longValue()
    {
        return ByteBuffer.wrap(word).getLong();
    }

    @Override
    public float floatValue()
    {
        return ByteBuffer.wrap(word).getInt().getFloat();
    }

    @Override
    public double doubleValue()
    {
        return ByteBuffer.wrap(word).getInt();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (int i = bitCount(size) - 1; i >= 0; i--)
        {
            sb.append(this.getBit(i));
        }

        return sb.toString();
    }
}