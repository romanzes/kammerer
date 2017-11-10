package ru.romanzes.kammerer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Environment {
	private List<String> variableNames = new ArrayList<>();
	private Map<String, List<Integer>> variableMap = new HashMap<>();
	
	private static int[] convertToIntArray(byte[] input) {
	    int[] result = new int[input.length];
	    for (int i = 0; i < input.length; i++) {
	        result[i] = input[i] & 0xff;
	    }
	    return result;
	}
	
	private static byte[] convertToByteArray(int[] input) {
	    byte[] result = new byte[input.length];
	    for (int i = 0; i < input.length; i++) {
	        result[i] = (byte) input[i];
	    }
	    return result;
	}
	
	public void setVariable(String variableName, String value) {
		setVariable(variableName, convertToIntArray(value.getBytes()));
	}
	
	public void setVariable(String variableName, int[] value) {
		List<Integer> array = new ArrayList<>();
		for (int element: value) {
			array.add(element);
		}
		variableNames.add(variableName);
		variableMap.put(variableName, array);
	}
	
	public void setVariableAtIndex(String variableName, Integer index, int value) {
		List<Integer> array = variableMap.get(variableName);
		if (array == null) {
			array = new ArrayList<>();
			variableNames.add(variableName);
			variableMap.put(variableName, array);
		}
		if (index == null || array.size() == 0) {
			array.add(value);
		} else if (index < 0 || index >= array.size()) {
			array.set(array.size() - 1, value);
		} else {
			array.set(index, value);
		}
	}
	
	public int getVariableSize(String variableName) {
		List<Integer> array = variableMap.get(variableName);
		if (array == null) {
			return 0;
		} else {
			return array.size();
		}
	}
	
	public String getVariableAsString(String variableName) {
		byte[] values = convertToByteArray(getVariable(variableName));
		return new String(values);
	}
	
	public int[] getVariable(String variableName) {
		List<Integer> array = variableMap.get(variableName);
		if (array == null) {
			return new int[0];
		}
		int[] result = new int[array.size()];
		for (int i = 0; i < array.size(); i++) {
			result[i] = array.get(i);
		}
		return result;
	}
	
	public int getVariableAtIndex(String variableName, Integer index) {
		List<Integer> array = variableMap.get(variableName);
		if (index == null || index < 0 || index >= array.size()) {
			if (array.size() > 0) {
				return array.get(array.size() - 1);
			} else {
				return 0;
			}
		} else {
			return array.get(index);
		}
	}
	
	public int getVariableCount() {
		return variableNames.size();
	}
	
	public String getVariableName(int index) {
		return variableNames.get(index);
	}
}
