import { describe, test, expect, vi } from 'vitest';
import productService from '../api/products';
import { upload, get, del, post } from '../methods';

// Mockeamos los métodos HTTP
vi.mock('../methods', () => ({
  upload: vi.fn(),
  get: vi.fn(),
  del: vi.fn(),
  post: vi.fn(),
}));

const mockFile = new File([''], 'mockImage.png');
const mockProduct = {
  id: 1,
  model: 'Mock Model',
  name: 'Mock Product',
  description: 'Mock Description',
  price: 100,
  stock: 10,
  size: 'M',
  color: 'Red',
  maintenance: 'Easy',
  material: 'Cotton',
  absorbency: 'High',
  material_feature: 'Soft',
  category_id: 1,
};

describe('Product Service', () => {
  test('setProduct should upload a product', async () => {
    const mockResponse = { id: 1, name: 'Mock Product' };
    (upload as jest.Mock).mockResolvedValue(mockResponse);

    const result = await productService.product.setProduct(mockFile, mockProduct);

    expect(upload).toHaveBeenCalledWith('/products/upload', mockFile, mockProduct);
    expect(result).toEqual(mockResponse);
  });

  test('getProducts should retrieve products and map them with URLs', async () => {
    const mockProdInfo = {
      folder: 'mockFolder',
      products: [{ ...mockProduct, hash: 'abc123', type: 'jpg' }],
    };
    (get as jest.Mock).mockResolvedValue(mockProdInfo);

    const [products, folder] = await productService.product.getProducts();

    expect(get).toHaveBeenCalledWith('/products');
    expect(folder).toBe('mockFolder');
  });

  test('deleteProduct should delete a product', async () => {
    (del as jest.Mock).mockResolvedValue({});

    await productService.product.deleteProduct(mockProduct.id);

    expect(del).toHaveBeenCalledWith(`/products/${mockProduct.id}`);
  });

  test('updateProduct should upload updated product data', async () => {
    await productService.product.updateProduct(mockFile, mockProduct, mockProduct.id);

    expect(upload).toHaveBeenCalledWith(`/products/${mockProduct.id}`, mockFile, mockProduct, false);
  });
});
