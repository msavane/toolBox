"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var react_1 = require("react");
var card_1 = require("@/components/ui/card");
var button_1 = require("@/components/ui/button");
var lucide_react_1 = require("lucide-react");
var axios_1 = require("axios");
var AgriEcommerce = function () {
    var _a = (0, react_1.useState)([]), products = _a[0], setProducts = _a[1];
    var _b = (0, react_1.useState)(true), loading = _b[0], setLoading = _b[1];
    var _c = (0, react_1.useState)(null), error = _c[0], setError = _c[1];
    (0, react_1.useEffect)(function () {
        axios_1.default.get("/api/products")
            .then(function (response) {
            if (response.data && Array.isArray(response.data)) {
                var validProducts = response.data.filter(function (product) {
                    return (product === null || product === void 0 ? void 0 : product.id) && typeof (product === null || product === void 0 ? void 0 : product.name) === "string" && typeof (product === null || product === void 0 ? void 0 : product.description) === "string" && typeof (product === null || product === void 0 ? void 0 : product.price) === "number";
                });
                setProducts(validProducts);
            }
            else {
                throw new Error("Invalid response format");
            }
        })
            .catch(function (error) {
            var _a, _b;
            console.error("Error fetching products:", ((_b = (_a = error.response) === null || _a === void 0 ? void 0 : _a.data) === null || _b === void 0 ? void 0 : _b.message) || error.message || error);
            setError("Failed to load products. Please try again later.");
        })
            .finally(function () { return setLoading(false); });
    }, []);
    return (<div className="min-h-screen bg-gradient-to-b from-yellow-400 to-purple-600 p-8">
      <h1 className="text-4xl font-bold text-center text-white mb-6">Agri E-commerce</h1>
      {loading && <p className="text-center text-white">Loading products...</p>}
      {error && <p className="text-center text-red-500">{error}</p>}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {products.length > 0 ? (products.map(function (product) { return (<card_1.Card key={product.id} className="bg-white shadow-xl rounded-2xl">
              <card_1.CardContent className="p-4">
                <h2 className="text-2xl font-semibold text-purple-700">{product.name}</h2>
                <p className="text-gray-600">{product.description}</p>
                <p className="text-yellow-500 font-bold text-lg">${product.price.toFixed(2)}</p>
                <button_1.Button className="mt-4 w-full bg-purple-700 text-white flex items-center justify-center gap-2">
                  <lucide_react_1.ShoppingCart size={20}/> Add to Cart
                </button_1.Button>
              </card_1.CardContent>
            </card_1.Card>); })) : (!loading && !error && <p className="text-center text-white">No products available.</p>)}
      </div>
    </div>);
};
exports.default = AgriEcommerce;
