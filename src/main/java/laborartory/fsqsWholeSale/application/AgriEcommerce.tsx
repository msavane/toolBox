import React, { useEffect, useState } from "react";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { ShoppingCart } from "lucide-react";
import axios from "axios";

const AgriEcommerce = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios.get("/api/products")
      .then(response => {
        if (response.data && Array.isArray(response.data)) {
          const validProducts = response.data.filter(product =>
            product?.id && typeof product?.name === "string" && typeof product?.description === "string" && typeof product?.price === "number"
          );
          setProducts(validProducts);
        } else {
          throw new Error("Invalid response format");
        }
      })
      .catch(error => {
        console.error("Error fetching products:", error.response?.data?.message || error.message || error);
        setError("Failed to load products. Please try again later.");
      })
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="min-h-screen bg-gradient-to-b from-yellow-400 to-purple-600 p-8">
      <h1 className="text-4xl font-bold text-center text-white mb-6">Agri E-commerce</h1>
      {loading && <p className="text-center text-white">Loading products...</p>}
      {error && <p className="text-center text-red-500">{error}</p>}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {products.length > 0 ? (
          products.map((product) => (
            <Card key={product.id} className="bg-white shadow-xl rounded-2xl">
              <CardContent className="p-4">
                <h2 className="text-2xl font-semibold text-purple-700">{product.name}</h2>
                <p className="text-gray-600">{product.description}</p>
                <p className="text-yellow-500 font-bold text-lg">${product.price.toFixed(2)}</p>
                <Button className="mt-4 w-full bg-purple-700 text-white flex items-center justify-center gap-2">
                  <ShoppingCart size={20} /> Add to Cart
                </Button>
              </CardContent>
            </Card>
          ))
        ) : (
          !loading && !error && <p className="text-center text-white">No products available.</p>
        )}
      </div>
    </div>
  );
};

export default AgriEcommerce;
