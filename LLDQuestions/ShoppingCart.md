## Functional requirements
- Users can add products, update their quantities
- A cart belongs to a user
- adding an item to the cart should reflect the inventory (reserve the inventory items)
    - follow up question, what if the user has just added the product in the cart and never purchased should we block that item permanently?
    - I think no
- A cart can be converted to the order by making the booking
- user can apply coupons to their cart
- cart should be able to track the product, their listing, their quantities, total price
- can user apply multiple coupons? or only single coupon is allowed?

Design and implement the backend class structure for a Shopping Cart system with basic operations and validations.

## Requirements:

Build the core logic to:

addItem(productId, quantity): Add an item to the cart
viewCart(): View current items and their quantities
removeItem(productId): Remove an item from the cart
checkout(): Finalize the cart for purchase

## Edge Cases to Handle:

Trying to add out-of-stock items
Cart exceeding a predefined size or item limit
Attempting to checkout with an empty cart

## Focus Areas:

Object-oriented design (OOP principles: encapsulation, abstraction)
Modeling entities like Cart, CartItem, ProductCatalog
Basic validation logic before operations
Code readability and extensibility for future features (e.g., promotions, discounts)

## Follow-up Discussion Points:

How would you handle concurrency (e.g., multiple sessions editing the cart)?
How would you extend this for multiple users?
How would you support inventory sync and reservation during checkout?
