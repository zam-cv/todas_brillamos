// src/utils/__tests__/apiOrders.test.ts

import { test, expect, vi } from 'vitest';
import ordersAPI from '../api/orders';
import { get } from '../methods'; 

vi.mock('../methods', () => ({
  get: vi.fn(),
}));

test('should fetch all orders', async () => {
  const mockOrders = {
    orders: [
      { Quantity: 2, DeliveryDate: '2024-01-01', Status: 'Shipped', ProductID: 1, ClientID: 1 },
      { Quantity: 1, DeliveryDate: '2024-01-02', Status: 'Pending', ProductID: 2, ClientID: 2 },
    ],
  };

  (get as jest.Mock).mockResolvedValueOnce(mockOrders);

  const result = await ordersAPI.orders.getOrders();

  expect(result).toEqual(mockOrders);
  expect(get).toHaveBeenCalledWith("/orders/all");
});

test('should fetch order information based on client ID', async () => {
  const clientId = 1;
  const mockOrderInfo = [
    { id: 1, first_name: 'John', last_name: 'Doe', email: 'john@example.com', price: 100, quantity: 2, product_id: 1, client_id: clientId, total_price: 200 },
  ];

  (get as jest.Mock).mockResolvedValueOnce(mockOrderInfo);

  const result = await ordersAPI.orders.getOrdersInfo(); 

  expect(result).toEqual(mockOrderInfo);
  expect(get).toHaveBeenCalledWith(`/orders/info`);
});

test('should fetch most sold products based on product ID', async () => {
  const productId = 1;
  const mockMostSell = [
    { id: productId, name: 'Product 1', order_count: 10 },
  ];

  (get as jest.Mock).mockResolvedValueOnce(mockMostSell);

  const result = await ordersAPI.orders.getMostSell(); 

  expect(result).toEqual(mockMostSell);
  expect(get).toHaveBeenCalledWith(`/orders/BestSell`);
});

test('should fetch monthly revenue based on specific date range', async () => {
  const mockMonthlyRevenue = [
    { month: 'January', total_revenue: 1000 },
  ];

  (get as jest.Mock).mockResolvedValueOnce(mockMonthlyRevenue);

  const result = await ordersAPI.orders.getMonthlyRevenue(); 

  expect(result).toEqual(mockMonthlyRevenue);
  expect(get).toHaveBeenCalledWith(`/orders/monthRev`);
});
