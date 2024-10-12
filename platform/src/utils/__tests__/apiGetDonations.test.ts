// src/utils/__tests__/donations.test.ts

import { test, expect, vi } from 'vitest';
import donationsAPI from '@/utils/api/donations';
import { get } from '@/utils/methods';

vi.mock('@/utils/methods', () => ({
  get: vi.fn(),
}));

test('should fetch all donations', async () => {
  const mockDonations = [
    { amount: 100, description: 'Donation 1', date: '2024-01-01' },
    { amount: 200, description: 'Donation 2', date: '2024-01-02' },
  ];

  (get as jest.Mock).mockResolvedValueOnce(mockDonations); // Mockear la respuesta de la API

  const result = await donationsAPI.donations.getDonations(); // Llamar a la función

  expect(result).toEqual(mockDonations); // Verificar el resultado
  expect(get).toHaveBeenCalledWith("/donations"); // Verificar la llamada a la API
});
