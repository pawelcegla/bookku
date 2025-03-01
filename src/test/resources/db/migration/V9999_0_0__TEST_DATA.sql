INSERT INTO bookmark (slug, target, restricted) VALUES
  ('foo', 'test://flight.of.opportunity', 0),
  ('bar', 'test://brain.access.router', 0),
  ('123', 'test://one.two.three', 0),
  ('857620a5-79ed-4988-8439-382b912ef943', 'test://undefined.unsafe.initial.design', 0),
  ('d-_-b', 'test://dee.bee', 0);

-- tester/tester
INSERT INTO owner (name, hashed_password) VALUES
  ('tester', '$argon2id$v=19$m=16384,t=2,p=1$57JrVoS/ZpxgVLuVbc8y3g$TDjBlqnB04qO4Dy/21k6MCFOIPX3m1Ji9E1+xXeKj0s');
