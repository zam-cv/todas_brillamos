// src/utils/__tests__/notifications.test.ts

import { test, expect, vi } from 'vitest';
import notificationsAPI from '../api/notifications'; // Ajusta la ruta según tu estructura de carpetas
import { get, post } from '../methods'; // Ajusta la ruta según tu estructura de carpetas

vi.mock('../methods', () => ({
  get: vi.fn(),
  post: vi.fn(),
}));

test('should fetch all notifications', async () => {
  const mockNotifications = [
    { id: '1', title: 'Notification 1', description: 'Description 1', date: '2024-01-01', client_id: 1 },
    { id: '2', title: 'Notification 2', description: 'Description 2', date: '2024-01-02', client_id: 2 },
  ];

(get as jest.Mock).mockResolvedValueOnce(mockNotifications); // Mockear la respuesta de la API

  const result = await notificationsAPI.notification.getNotifications(); // Llamar a la función

  expect(result).toEqual(mockNotifications); // Verificar el resultado
  expect(get).toHaveBeenCalledWith("/notifications"); // Verificar la llamada a la API
});

test('should set a new notification', async () => {
  const newNotification = { id: '3', title: 'Notification 3', description: 'Description 3', date: '2024-01-03', client_id: 1 };

  (post as jest.Mock).mockResolvedValueOnce(newNotification); // Mockear la respuesta de la API

  const result = await notificationsAPI.notification.setNotification(newNotification); // Llamar a la función

  expect(result).toEqual(newNotification); // Verificar el resultado
  expect(post).toHaveBeenCalledWith(`/notifications/${newNotification.client_id}`, newNotification); // Verificar la llamada a la API
});
