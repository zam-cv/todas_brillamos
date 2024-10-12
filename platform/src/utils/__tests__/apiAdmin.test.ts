// src/utils/__tests__/admin.test.ts

import { test, expect, vi } from 'vitest';
import adminAPI from '../api/admin'; // Ajusta la ruta según la estructura de carpetas
import { get, post } from '@/utils/methods';

vi.mock('@/utils/methods', () => ({
  get: vi.fn(),
  post: vi.fn(),
}));

test('should login an admin', async () => {
  const mockCredentials = {
    token: 'mockToken123',
  };

  const email = 'admin@example.com';
  const password = 'password123';

  (post as jest.Mock).mockResolvedValueOnce(mockCredentials);

  const result = await adminAPI.admin.loginAdmin(email, password);

  expect(result).toEqual(mockCredentials);
  expect(post).toHaveBeenCalledWith("/auth/admin/signin", { email, password }, false);
});

test('should verify admin', async () => {
  const mockAdmin = {
    email: 'admin@example.com',
    password: 'password123',
  };

  (get as jest.Mock).mockResolvedValueOnce(mockAdmin);

  const result = await adminAPI.admin.verifyAdmin();

  expect(result).toEqual(mockAdmin);
  expect(get).toHaveBeenCalledWith("/auth/admin/verify");
});
