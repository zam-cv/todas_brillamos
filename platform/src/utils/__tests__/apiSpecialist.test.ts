// src/utils/__tests__/specialist.test.ts

import { test, expect, vi } from 'vitest';
import specialistAPI from '../api/specialist'; // Ajusta la ruta según tu estructura de carpetas
import { get, post } from '../methods'; // Ajusta la ruta según tu estructura de carpetas

vi.mock('../methods', () => ({
  get: vi.fn(),
  post: vi.fn(),
}));

test('should create a new specialist', async () => {
  const mockSpecialist = {
    id: 1,
    FirstName: 'John',
    LastName: 'Doe',
    phone: '123456789',
    specialty: 'Cardiology',
    description: 'Heart specialist',
  };

  (post as jest.Mock).mockResolvedValueOnce(mockSpecialist); // Mockear la respuesta de la API

  const result = await specialistAPI.specialist.setSpecialist(mockSpecialist);

  expect(result).toEqual(mockSpecialist); // Verificar el resultado
  expect(post).toHaveBeenCalledWith("/specialists", mockSpecialist); // Verificar que se llamó el endpoint correcto
});

test('should fetch specialists', async () => {
  const mockSpecialists = [
    { id: 1, FirstName: 'John', LastName: 'Doe', phone: '123456789', specialty: 'Cardiology', description: 'Heart specialist' },
    { id: 2, FirstName: 'Jane', LastName: 'Smith', phone: '987654321', specialty: 'Neurology', description: 'Brain specialist' },
  ];

  (get as jest.Mock).mockResolvedValueOnce(mockSpecialists);

  const result = await specialistAPI.specialist.getSpacialist();

  expect(result).toEqual(mockSpecialists);
  expect(get).toHaveBeenCalledWith("/specialist");
});
