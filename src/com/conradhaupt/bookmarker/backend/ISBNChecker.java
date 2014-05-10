package com.conradhaupt.bookmarker.backend;

public class ISBNChecker {
	/*
	 * This class checks for errors in ISBN numbers and returns a boolean
	 * condition if the input number is valid or not. It can calculate ISBN 10
	 * and 13 codes.
	 */

	public static boolean isISBN(String isbn) {
		boolean isIsbn10 = isISBN10(isbn);
		boolean isIsbn13 = isISBN13(isbn);
		if (isIsbn10 || isIsbn13) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isISBN(String isbn, int type) {
		switch (type) {
		case ISBN.TYPE_10:
			isISBN10(isbn);
		case ISBN.TYPE_13:
			return isISBN13(isbn);
		default:
			return false;
		}
	}

	private static boolean isISBN13(String isbn) {
		// Handle incorrect lengths
		if (isbn.length() != 13) {
			return false;
		}
		// Process ISBN prefix
		if ((isbn.indexOf("979") == 0) || (isbn.indexOf("978") == 0)) {
			// Proceed with check digit calculation
			int checkDigitSum = 0;
			for (int i = 0; i < 12; i++) {
				checkDigitSum += (Integer.parseInt(isbn.charAt(i) + "") * Math
						.pow(3, i % 2));
			}

			// Check for only numbers
			try {
				Long.parseLong(isbn);
			} catch (Exception e) {
				return false;
			}

			// Compare to actual check digit
			int checkDigit = (10 - (checkDigitSum % 10)) % 10;
			System.out.println("Check digit for " + isbn + " found to be "
					+ checkDigit + " and check digit sum to be "
					+ checkDigitSum);
			if (checkDigit != (Integer.parseInt(isbn.charAt(12) + ""))) {
				// The check digit does not correspond, return false
				return false;
			}
		}

		// All conditions met, return true
		return true;
	}

	public static boolean isISBN10(String isbn) {
		// Compute sum of 9 digits
		int checkDigitSum = 0;
		for (int i = 10; i > 1; i--) {
			checkDigitSum += (Integer.parseInt(isbn.charAt(10 - i) + "")) * i;
		}

		// Compare resultant check digit to actual
		if ((11 - (checkDigitSum % 11)) != Integer
				.parseInt(isbn.charAt(9) + "")) {
			// Check digits do not correlate, return false
			return false;
		}

		// All conditions met, return true
		return true;
	}

	public static String convertToISBNCharSequence(String encodedISBN)
			throws Exception {
		// Create a temporary string variable to store the final result
		String output = "";
		// Create string variables for characters to be processed
		final String CHARACTERS = "1234567890xX";
		final String CHARACTERS_10 = "xX";
		final String SPACE = " ";
		final String HYPHEN = "-";

		// Remove spaces and hyphens
		encodedISBN = encodedISBN.replace(SPACE, "").replace(HYPHEN, "");

		// Go through each character and add to output if it is a suitable
		// character
		for (int i = 0; i < encodedISBN.length(); i++) {
			// Check for character compliance
			if (CHARACTERS.contains(encodedISBN.charAt(i) + "")) {
				output = output + encodedISBN.charAt(i);
			} else {
				// If the other character is not a compliant character
				throw new Exception(
						"ISBNFormatError: Invalid character encountered");
			}
		}

		// Check for usage of 'X' in any character
		int indexOfx = output.indexOf(CHARACTERS_10.charAt(0) + "");
		int indexOfX = output.indexOf(CHARACTERS_10.charAt(1) + "");
		// Process indexes of the letter 'X'
		if ((indexOfx > -1 && indexOfx < output.length() - 1)
				|| (indexOfX > -1 && indexOfX < output.length() - 1)) {
			// An X occurs where it's not meant to
			System.out.println(indexOfx + " " + indexOfX + " "
					+ output.length());
			throw new Exception(
					"ISBNFormatError: Invalid character encountered");
		}

		// Check for the correct lengths
		if (!(output.length() == 10 || output.length() == 13)) {
			throw new Exception("ISBNFormatError: Invalid isbn number length");
		}

		// Output the resultant isbn
		return output;
	}

	// Holder class for constants
	public class ISBN {
		public static final int TYPE_13 = 0;
		public static final int TYPE_10 = 1;
	}
}
