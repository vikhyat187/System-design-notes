# Singleton 

## Eager initialisation

<img width="602" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/420865d3-a6bd-4792-8398-39e3984895bb">

## Lazy 

<img width="469" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/4e7deb68-3148-453d-9ae5-2921c58488ec">

## Synchronised

It takes lock on each of the request, its very expensive and never used in the industry
<img width="507" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/39b9bcad-cbe7-4731-9dba-e6551869a364">

## Double locking 

Here before taking the lock we check if the conn obj is `null` or not, if `null` then take the lock or else not.

<img width="663" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/78664257-a3e5-4a53-a2a9-5d0de2cd122a">
