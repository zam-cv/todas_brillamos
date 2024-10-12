import { test, expect, vi, beforeEach } from 'vitest';
import { getConfig } from '../auth'; // Ajusta la ruta si es necesario

// Antes de todos los tests, mockeamos localStorage
beforeEach(() => {
  const mockLocalStorage = {
    getItem: vi.fn(),
  };
  vi.stubGlobal('localStorage', mockLocalStorage)
});

test('should return the correct config object with token', () => {
  const mockToken = 'mockedToken123';

  // Simulamos que localStorage devuelve un token
  (localStorage.getItem as jest.Mock).mockReturnValue(mockToken);

  const config = getConfig();

  expect(config).toEqual({
    withCredentials: true,
    headers: {
      Authorization: 'Bearer ' + mockToken,
    }
  });

  expect(localStorage.getItem).toHaveBeenCalledWith("token");
});

test('should return config with null token if no token is in localStorage', () => {
  // Simulamos que localStorage devuelve null
  (localStorage.getItem as jest.Mock).mockReturnValue(null);

  const config = getConfig();

  expect(config).toEqual({
    withCredentials: true,
    headers: {
      Authorization: 'Bearer ' + null,
    }
  });

  expect(localStorage.getItem).toHaveBeenCalledWith("token");
});
