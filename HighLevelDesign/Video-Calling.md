## FR 
1 : 1 calls
group calls
audio / video / screen share
record the video

## NFR
- super fast
- high availability
- data loss is ok

TCP 
- 20 bytes header
- 3 way handshake
- lossless protocol
- sequence number
- It retries in case of data not being sent
- websocket is built on TCP

UDP
- 8 bytes header
- lossy protocol
- out of order packets

- For recording we need to use the call server, which records the call
- In case of P2P we cannot record the data

<img width="904" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/19b3cc99-0a02-4575-acef-a12208d3cb87">
 <img width="900" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/d018de9c-f62d-4758-b467-36274bdf5706">
<img width="844" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/6fb5476c-6b75-4a6a-bc6f-3615c0591a3d">

- Signalling service
  - It calls the another client and asks for its agreement for accepting the call
- Connector (STUN server)
  - used to get public IP address
  - 
