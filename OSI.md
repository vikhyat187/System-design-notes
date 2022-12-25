| Layers      | Example |
| :--- | :---: |
| Layer 7 Application      | Sending via phone       |
| Layer 6 Presentation layer   | Used in http to encrypt the data        |
| Layer 5 Session layer | used to identify the data based on session id |
| Layer 4 transport layer | bunch of bits are divided into segments, are tagged with port no's |
| Layer 3 Network layer | takes the segment and attaches source and destination ip address, its called packet|
| Layer 2 Data link layer | it takes the packet and converts to frame, it adds MAC address|
| Layer 1 Physical layer |  it takes the data in frames and sends in bits, bits are sneaked into electric signals, wifi signals light signals and radio signals |


Every data frame is sent to every one, its the job of network card to discard not related ones
