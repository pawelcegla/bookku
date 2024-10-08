INSERT INTO bookmark (hasz, target) VALUES
  ('foo', 'test://flight.of.opportunity'),
  ('bar', 'test://brain.access.router'),
  ('123', 'test://one.two.three'),
  ('d-_-b', 'test://dee.bee');

-- tester/tester
INSERT INTO owner (owner_name, hashed_password) VALUES
  ('tester', '$argon2id$v=19$m=16384,t=2,p=1$57JrVoS/ZpxgVLuVbc8y3g$TDjBlqnB04qO4Dy/21k6MCFOIPX3m1Ji9E1+xXeKj0s');
