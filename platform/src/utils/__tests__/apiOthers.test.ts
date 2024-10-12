// src/utils/__tests__/others.test.ts

import { test, expect, vi } from 'vitest';
import othersAPI from '../api/others'; // Ajusta la ruta según tu estructura de carpetas
import { get, upload } from '../methods'; // Ajusta la ruta según tu estructura de carpetas

vi.mock('../methods', () => ({
  get: vi.fn(),
  upload: vi.fn(),
}));

test('should set others with file upload', async () => {
  const mockOthers = {
    CURP: 'ABC123456789',
    Street: 'Main Street',
    Interior: 'Apt 101',
    Exterior: '12B',
    City: 'CityName',
    State: 'StateName',
    ZIP: '12345',
    Reference: 'Near the park',
    ClientID: null,
  };
  const mockFile = new File(['file content'], 'others.pdf');

  (upload as jest.Mock).mockResolvedValueOnce(undefined); // Simular la respuesta del upload

  const result = await othersAPI.others.setOthers(mockOthers, mockFile);

  expect(result).toBeUndefined(); // Verificar que la promesa resuelva sin valor
  expect(upload).toHaveBeenCalledWith("admin/others", mockFile, mockOthers, false); // Verificar que el endpoint y los parámetros sean correctos
});

test('should fetch others', async () => {
  (get as jest.Mock).mockResolvedValueOnce(undefined); // Simular la respuesta del get

  const result = await othersAPI.others.getOthers();

  expect(result).toBeUndefined(); // Verificar que la promesa resuelva sin valor
  expect(get).toHaveBeenCalledWith("admin/others"); // Verificar que el endpoint sea correcto
});
