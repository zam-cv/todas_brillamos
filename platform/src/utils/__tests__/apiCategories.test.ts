// src/utils/__tests__/categories.test.ts

import { test, expect, vi } from 'vitest';
import categoriesAPI from '@/utils/api/category';
import { post, get} from '@/utils/methods';

vi.mock('@/utils/methods', () => ({
    get: vi.fn(),  // Asegúrate de incluir esta línea
    post: vi.fn(),
    del: vi.fn(),
  }));
  

test('should fetch all categories', async () => {
  const mockCategories = [
    { id: 1, name: 'Category 1' },
    { id: 2, name: 'Category 2' },
  ];

  (get as jest.Mock).mockResolvedValueOnce(mockCategories); // Mockear la respuesta de la API

  const result = await categoriesAPI.category.getCategories(); // Llamar a la función

  expect(result).toEqual(mockCategories); // Verificar el resultado
  expect(get).toHaveBeenCalledWith("/categories"); // Verificar la llamada a la API
});

test('should set a new category', async () => {
  const newCategory = { id: 3, name: 'Category 3' };

  (post as jest.Mock).mockResolvedValueOnce(newCategory); // Mockear la respuesta de la API

  const result = await categoriesAPI.category.setCategory(newCategory); // Llamar a la función

  expect(result).toEqual(newCategory); // Verificar el resultado
  expect(post).toHaveBeenCalledWith("/categories", newCategory); // Verificar la llamada a la API
});

